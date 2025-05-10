# Stage 1: Build the Angular App
FROM node:18 AS build

WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the rest of the app and build
COPY . .
RUN npm run build --prod

# Stage 2: Serve with Nginx
FROM nginx:latest

# Remove default nginx config
RUN rm -rf /etc/nginx/conf.d

# Copy custom Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy Angular build output to Nginx's web directory
COPY --from=build /app/dist/ui/browser /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
