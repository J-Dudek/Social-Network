### STAGE 1: Build ###


FROM node:14.5.0-alpine

COPY package.json yarn.lock ./

## Storing node modules on a separate layer will prevent unnecessary npm installs at each build
RUN yarn install && mkdir /app && mv ./node_modules ./app

## Move to /app
WORKDIR /app


# Copy everything from host to /app in the container
COPY . .

## Build the react app in dev mode and store the artifacts in dist folder

RUN yarn install

EXPOSE 3000

# start app
CMD ["yarn", "start"]