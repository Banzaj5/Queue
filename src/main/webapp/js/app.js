var app = angular.module('queue',
    [

    ]);
app.config(['$stateProvider','$urlRouterProvider', function($stateProvider, $urlRouterProvider){
    $urlRouterProvider.otherwise('index');

    $stateProvider.state('index', {
        url: '/',
        templateUrl: 'index.html'
    });
    $stateProvider.state('test', {
        url: '/test',
        templateUrl: 'html/test.html'
    });
}]);