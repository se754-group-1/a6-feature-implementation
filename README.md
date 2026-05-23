# Immediate Feedback (EN-01)

After every quiz attempt, the system instantly evaluates the response and delivers targeted feedback. This helps users by explaining mistakes, signposting what they got right, and guiding them toward the best next step — whether revisiting material, retrying, or progressing to a new topic.

## How It Works

- **Correct answer:** User is congratulated and prompted to advance to the next topic or retry a similar question.
- **Incorrect answer:** User receives an explanation of why their answer was wrong, a prompt to revisit the relevant learning material, and the option to attempt a similar question.
# a6-feature-implementation

## Notes and Prerequisites

**Prerequisites:**

- Docker compose [installation](https://docs.docker.com/compose/install/)
- For Windows User to run the commands you can use Git Bash or alternatively use WSL, see [installation](https://learn.microsoft.com/en-us/windows/wsl/install). Then navigate to the project's directory via WSL.

**Notes:**

- This project intentionally uses Java 21 from the start; do not rely on Java 17 unless a tool (e.g., JMeter) requires it.
- The Docker image includes a healthcheck that verifies `/api/answer` returns a successful response.

**Commands**:

This project runs on Java 21 (compiled and tested with OpenJDK 21).

Run the app using Docker Compose:

```bash
chmod +x start.sh
./start.sh
```

The command builds the application image and starts both the Spring Boot service and the JMeter UI container in the foreground.

When the app is running, the Feedback API is available as a POST endpoint at `http://localhost:8080/api/answer`.

For a browser-friendly readiness check, visit `http://localhost:8080/api/health`.

The POST request must include the header:

```bash
-H "Content-Type: application/json"
```

and a JSON body like:

```json
{
  "questionId": "q1",
  "answer": "A",
  "userId": "u1"
}
```

Example API calls:

```bash
curl -s -H "Content-Type: application/json" \
  -d '{"questionId":"q1","answer":"A","userId":"u1"}' \
  http://localhost:8080/api/answer
```

```bash
curl -s -H "Content-Type: application/json" \
  -d '{"questionId":"q2","answer":"B","userId":"u2"}' \
  http://localhost:8080/api/answer
```

```bash
curl -s -H "Content-Type: application/json" \
  -d '{"questionId":"q3","answer":"true","userId":"u3"}' \
  http://localhost:8080/api/answer
```

## JMeter Performance Test

The included test plan is stored at `src/test/resources/performancetest/loadtestplan.jmx`.
The included stress test is stored at `src/test/resources/performancetest/stresstestplan.jmx`.

Run the app and the JMeter container together using Docker Compose:

```bash
chmod +x start.sh
./start.sh
```

This starts the `app` and the JMeter UI container together. Access the browser UI at:

```bash
http://localhost:8081
```

Open the test plan from `/test/src/test/resources/performancetest/testplan.jmx` and run any JMeter test manually from the GUI.

To start an interactive shell inside the running JMeter UI container:

```bash
docker compose exec jmeter-ui /bin/bash
```

Then run any JMeter command manually, for example:

```bash
jmeter -n -t src/test/resources/performancetest/testplan.jmx -l target/jmeter-results.jtl -j target/jmeter.log -Jserver.host=app -Jserver.port=8080
```

The JMeter results are written to:

- `src/test/resources/performancetest/load-test-results.csv`
- `src/test/resources/performancetest/stress-test-results.csv`

If you want to run JMeter locally instead of via Docker Compose, you can still use:

```bash
jmeter -n -t src/test/resources/performancetest/testplan.jmx -l target/jmeter-results.jtl -j target/jmeter.log
```

### Test plan configuration

- `Load Users` thread group
  - Load test: `20` virtual users, `5` second ramp-up, `10` loops
  - Stress test: `100` virtual users, `10` second ramp-up, `10` loops
- `HTTP Request Defaults`
  - sets the base API host and port
- `Submit Correct Answer` sampler
  - sends `POST /api/answer` with a correct answer
- `Submit Incorrect Answer` sampler
  - sends `POST /api/answer` with an incorrect answer
- `HTTP Header Manager`
  - adds `Content-Type: application/json`
- Assertions validate:
  - HTTP `200` response status
  - response time under `200 ms`
  - correct-answer response body
  - incorrect-answer response body
- `Summary Report`
  - captures response time, throughput, thread counts, success rate, and response codes