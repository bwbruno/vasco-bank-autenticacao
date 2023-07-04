# Vasco Bank - Autenticação

### Execução

Com o docker e o docker-compose instalados, execute os comandos a seguir.

```bash
./mvnw clean package -DskipTests=true
docker-compose build
docker-compose up
```

As aplicações irão rodar por padrão nos links:
- Api Autenticação: http://localhost:7004
- Documentação Swagger: http://localhost:7004/autenticacao/swagger-ui/index.html

As portas e outras configurações podem ser alteradas pelo arquivo `.env`.
