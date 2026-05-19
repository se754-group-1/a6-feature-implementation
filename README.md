# a6-feature-implementation

This project runs on Java 21 (compiled and tested with OpenJDK 21).

Run the app using the provided script from bash:

```bash
chmod +x start.sh
./start.sh
```

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

Notes:

- This project intentionally uses Java 21 from the start; do not rely on Java 17 unless a tool (e.g., JMeter) requires it.
- The Docker image includes a healthcheck that verifies `/api/answer` returns a successful response.
