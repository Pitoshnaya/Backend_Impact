start:
	docker compose up -d
stop:
	docker compose stop
logs:
	docker compose logs app --follow
run-tests:
	docker compose exec app ./mvnw test
cli:
	docker compose exec -it app bash