#compiling
FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

#deploying to nginx
FROM nginx:alpine
COPY --from=node /app/dist/poker-tracker-client /usr/share/nginx/html
