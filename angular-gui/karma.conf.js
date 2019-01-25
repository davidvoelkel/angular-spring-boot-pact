// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular/cli', 'pact'],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular/cli/plugins/karma'),

// Pact JS Configuration --- START
      // Start karma-pact, which starts the Pact mock server process(es) at the beginning of your karma session and terminates them at the end
      require('@pact-foundation/karma-pact'),
    ],
    pact: [
      // Define a Pact mock server running on port 1234 and storing the files recorded in the "pacts/" directory
      {cors: false, spec: 2, port: 1234, dir: 'pacts/', consumer: 'angular-user-service', provider: 'rest-user-service'},
    ],
    // Karma-Proxies for Pact to avoid CORS problems, beause Karma runs on a different port than the Pact mock server(s)
    proxies: {
      // Define one proxy per endpoint to proxy requests from our pact tests running in the Karma browser to the Pact mock server(s)
      '/api/user': 'http://localhost:1234/user'
    },
// Pact JS Configuration --- END

    client:{
      clearContext: false
    },
    coverageIstanbulReporter: {
      reports: [ 'html', 'lcovonly' ],
      fixWebpackSourcePaths: true
    },
    angularCli: {
      environment: 'dev'
    },
    reporters: ['progress', 'kjhtml'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'],
    singleRun: false
  });
};
