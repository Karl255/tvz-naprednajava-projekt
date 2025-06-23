FROM node:20-alpine AS builder
WORKDIR /app
COPY web/ ./
RUN npm ci && npm run build

FROM nginx:alpine
COPY web/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/build /usr/share/nginx/html
