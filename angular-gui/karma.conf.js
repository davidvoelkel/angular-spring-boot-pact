// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: [
      'jasmine',
      '@angular/cli',

// Pact JS Configuration --- START
      'pact'
    ],
    pact: [
      // Define a Pact mock server running on port 1234 and storing the files recorded in the "pacts/" directory
      {cors: true, spec: 2, port: 1234, dir: 'pacts/', consumer: 'angular-user-service', provider: 'rest-user-service'},
    ],
    plugins: [
      // Start karma-pact, which starts the Pact mock server process(es) at the beginning of your karma session and terminates them at the end
      require('@pact-foundation/karma-pact'),
// Pact JS Configuration --- END

      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular/cli/plugins/karma'),
    ],

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
