// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular/cli', 'pact'],
    plugins: [
      require('karma-jasmine'),
      require('@pact-foundation/karma-pact'),
      require('karma-chrome-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular/cli/plugins/karma')
    ],
    
    // Pact-JS Config
    pact: [ 
      {cors: false, spec: 2, port: 1234, dir: 'pacts/', consumer: 'angular-user-service', provider: 'rest-user-service'}, 
    ],
    proxies: { // Proxies for Pact
      // Define one proxy per endpoint to proxy requests from our pact tests running in the karma browser to the pact mock servers
      '/user': 'http://localhost:1234/user'
    },

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
