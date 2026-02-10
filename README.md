# Inditex Price Service

Servicio Spring Boot WebFlux para consultar el precio aplicable de un producto por fecha, cadena y producto, usando una base de datos en memoria inicializada con los datos del enunciado.

## Arquitectura (Hexagonal)
- `domain`: modelo `Price`, criterios y puertos.
- `application`: caso de uso `PriceQueryService`.
- `adapters/in/web`: controlador REST y DTOs.
- `adapters/out/persistence`: repositorio Spring Data R2DBC y mapeo de entidad.

## API

**GET** `/api/prices`

**Query params**
- `applicationDate` (ISO-8601, sin zona horaria). Ej: `2020-06-14T10:00:00`
- `productId` (long)
- `brandId` (long)

**Respuesta 200**
```json
{
  "brandId": 1,
  "productId": 35455,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

**Ejemplo curl**
```bash
curl "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

**Errores**
- `400` parametros invalidos: respuesta estandar de Spring (RFC 7807).
- `404` sin precio aplicable: respuesta `PriceErrorResponse`.

Ejemplo 404:
```json
{
  "timestamp": "2025-01-01T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "No price found for brandId=1, productId=35455, applicationDate=2020-06-14T10:00",
  "path": "/api/prices"
}
```

## Persistencia y datos
- H2 en memoria con inicializacion via `DatabaseInitializerConfig` usando `schema.sql` y `data.sql`.
- Spring Data R2DBC con pool configurado via `spring.r2dbc.pool.*`.

## Notas de rendimiento
- Consulta optimizada por `brand_id`, `product_id` y rango de fechas con `LIMIT 1` y orden por `priority`, `start_date` e `id` para desempate determinista.
- Indice compuesto en `prices` para acelerar la busqueda y soportar el ordenamiento.

## Como ejecutar
```bash
./gradlew bootRun
```

El servicio queda en `http://localhost:8080`.

## Tests
```bash
./gradlew test
```

Incluye:
- Unit tests del caso de uso.
- Integration tests del repositorio R2DBC.
- E2E tests del endpoint REST.

## Docker
```bash
docker build -t inditex-assessment .
docker run --rm -p 8080:8080 inditex-assessment
```

## HTTP Requests (IntelliJ)
- `http/price-requests.http`
