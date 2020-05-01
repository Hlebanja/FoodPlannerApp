# Server – ExpressJS Backend

This [ExpressJS](https://expressjs.com/) template provides the basic infrastructure for a JSON API with MongoDB persistency with [Mongoose](https://mongoosejs.com/).

* [FAQ](docs/FAQ.md)
* [Troubleshooting](docs/TROUBLESHOOTING.md)

## Requirements

* [Node.js](https://nodejs.org/en/download/) (v10) => installation instructions for [Linux](https://github.com/nodesource/distributions), use installers for macOS and Windows (don't forget to restart your Bash shell)
* [MongoDB](https://www.mongodb.com/download-center/community?jmp=nav) (v4) must be running locally on port 27017 => installation instructions for [macOS](https://github.com/joe4dev/dit032-setup/blob/master/macOS.md#mongodb), [Windows](https://github.com/joe4dev/dit032-setup/blob/master/Windows.md#mongodb), [Linux](https://github.com/joe4dev/dit032-setup/blob/master/Linux.md#mongodb)
* [Postman](https://www.getpostman.com/downloads/) (v7) for API testing

## Project setup

Make sure, you are in the server directory `cd server`

Installs all project dependencies specified in [package.json](./package.json).

```bash
npm install
```

## Start the server with auto-restarts for development

Automatically restarts your server if you save any changes to local files.

```bash
npm run dev
```

## Start the server

```bash
npm start
```

## Run the Postman Tests

Starts a new server on another port (default `3001`) and runs the `server` postman test collection against a test database (default `serverTestDB`).

```bash
npm test
```

> The test database is dropped before each test execution. Adjust your tests to support this clean state.

## Postman Tests

* You can import the Postman collection from [tests/server.postman_collection.json](./tests/server.postman_collection.json)
* [Extracting data from responses and chaining requests](http://blog.getpostman.com/2014/01/27/extracting-data-from-responses-and-chaining-requests/)
* For the final submission, export the Postman collection into `tests/api-tests.postman.json` and make sure `npm test` completes successfully.

> Remember to **export and commit** any test changes back to `tests/server.postman_collection.json` and make sure `npm test` succeeds for your final submission!

## Debugging with VSCode

Set a breakpoint and click *Debug > Start Debugging*

> Learn more in the [VSCode Debugging Docs](https://code.visualstudio.com/docs/editor/debugging).
