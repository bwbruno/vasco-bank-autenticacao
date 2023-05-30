build:
	./mvnw clean install -DskipTests=true

run:
	docker-compose down
	docker-compose build
	docker-compose up -d
