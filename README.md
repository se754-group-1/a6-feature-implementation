# a6-feature-implementation

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

## JMeter performance test

The included test plan is stored at `src/test/resources/performancetest/testplan.jmx`.

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

- `target/jmeter-results.jtl`
- `target/jmeter.log`

If you want to run JMeter locally instead of via Docker Compose, you can still use:

```bash
jmeter -n -t src/test/resources/performancetest/testplan.jmx -l target/jmeter-results.jtl -j target/jmeter.log
```

### Test plan configuration

- `Load Users` thread group
  - `20` virtual users
  - `5` second ramp-up
  - `10` loops per user
  - total requests = `200`
- `HTTP Request Defaults`
  - sets the base API host and port
- `Submit Answer` sampler
  - sends `POST /api/answer`
  - request body is JSON: `{ "questionId": "q1", "answer": "A" }`
- `HTTP Header Manager`
  - adds `Content-Type: application/json`
- `Response Assertion`
  - checks the response body contains `Correct`
- `Summary Report`
  - captures metrics such as success, latency, thread counts, and response codes

Notes:

- This project intentionally uses Java 21 from the start; do not rely on Java 17 unless a tool (e.g., JMeter) requires it.
- The Docker image includes a healthcheck that verifies `/api/answer` returns a successful response.
