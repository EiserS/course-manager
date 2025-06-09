# 1) Build stage
FROM node:18-alpine AS build
WORKDIR /usr/src/app

# tylko pliki potrzebne do zainstalowania zależności
COPY package*.json ./
RUN npm ci

# reszta kodu
COPY . .
RUN npm run build

# 2) Serve stage
FROM nginx:stable-alpine
COPY --from=build /usr/src/app/build /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
