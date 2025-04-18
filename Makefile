build:
	mvn clean package

start: build
	docker-compose up -d

stop:
	docker-compose stop