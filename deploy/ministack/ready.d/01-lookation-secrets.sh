#!/bin/sh
# Seeds the local development secret into MiniStack's Secrets Manager.
#
# Must live under ready.d/: root-level init scripts run before the gateway accepts
# traffic, so an API call from there fails with "Could not connect".
set -eu

: "${AWS_DEFAULT_REGION:=us-east-1}"
: "${AWS_ACCESS_KEY_ID:=test}"
: "${AWS_SECRET_ACCESS_KEY:=test}"
: "${AWS_ENDPOINT_URL:=http://localhost:4566}"
export AWS_DEFAULT_REGION AWS_ACCESS_KEY_ID AWS_SECRET_ACCESS_KEY AWS_ENDPOINT_URL

ENDPOINT="${AWS_ENDPOINT_URL}"
NAME="lookation/local"

# Dev-only values. dbPassword must equal POSTGRES_PASSWORD in docker-compose.yml.
# jwtSecret and encryptionKey match the integration tests (server/src/test/.../SchemaSmokeTest.kt).
SECRET='{"dbPassword":"lookation","jwtSecret":"0123456789abcdef0123456789abcdef","encryptionKey":"AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=","adminPassword":"lookation-admin1!"}'

if aws --endpoint-url "$ENDPOINT" secretsmanager describe-secret --secret-id "$NAME" >/dev/null 2>&1; then
    aws --endpoint-url "$ENDPOINT" secretsmanager put-secret-value --secret-id "$NAME" --secret-string "$SECRET" >/dev/null
else
    # put-secret-value is not an upsert: the first seed after `docker compose up` needs create.
    aws --endpoint-url "$ENDPOINT" secretsmanager create-secret --name "$NAME" --secret-string "$SECRET" >/dev/null
fi

echo "seeded $NAME"
