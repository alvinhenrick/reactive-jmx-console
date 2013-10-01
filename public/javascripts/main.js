/*global require*/
'use strict';


define('angular', ['webjars!angular-locale_en-us.js'], function () {
    return angular;
});


require(['angular', 'app', './controllers', './routes', './directives', './filters', './services'],
    function (angular) {
        angular.bootstrap(document, ['jmxconsole']);
    });
