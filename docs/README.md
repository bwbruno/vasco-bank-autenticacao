# Vasco Bank - Autenticação

### Execução

Com o docker e o docker-compose instalados, execute os comandos a seguir.

```bash
./mvnw clean install -DskipTests=true
docker-compose build
docker-compose up

# ou, opcionalmente
make build run
```

As aplicações irão rodar por padrão nos links:
- Banco Postgres: http://localhost:5432
- Api Autenticação: http://localhost:543/swagger-ui/index.html2
- Documentação Swagger: http://localhost:8080/autenticacao/swagger-ui/index.html

As portas e outras configurações podem ser alteradas pelo arquivo `.env`.