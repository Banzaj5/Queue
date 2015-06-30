/**
 * Created by Arek on 2015-06-30.
 */

var app = angular.module('queue', []);

app.controller('QueueController', ['$scope', '$http', function($scope, $http) {
    $scope.operationSuccess = '';
    $scope.operationError = '';

    $scope.onSuccess = function(response) {
        $scope.operationSuccess = response;
    };
    $scope.onError = function(response) {
        $scope.operationError = response;
    };
    $scope.clearMessages = function() {
        $scope.operationSuccess = '';
        $scope.operationError = '';
    };

    $scope.addQueue = function(){
        $scope.clearMessages()
        $http.get('/Queue/addQueue').success(function(response) {
            $scope.onSuccess(response)
        }).error(function(response){
            $scope.onError(response);
        });
    };
    $scope.deleteQueue = function(){
        $scope.clearMessages();
        $http.get('/Queue/deleteQueue').success(function(response) {
            $scope.onSuccess(response);
        }).error(function(response){
            $scope.onError(response);
        });
    };
}]);
