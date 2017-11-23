(function(angular) {
    'use strict';
    var myApp = angular.module('Iot', []);

    myApp.controller('IotController', ['$scope', '$http', function($scope, $http) {

        $http.get('/api/devices').then(function (res) {
            $scope.devices = parseInt(res.data, 10);
        });
        $scope.updateDevices = function(incr) {
            if (($scope.devices || $scope.devices === 0) && $scope.devices + incr >= 0) {
                $scope.devices += incr;
                $http.put('/api/devices', $scope.devices);
            }
        };

        $http.get('/api/defectiveDevices').then(function (res) {
            $scope.defectiveDevices = parseInt(res.data, 10);
        });
        $scope.updateDefectiveDevices = function(incr) {
            if (($scope.defectiveDevices || $scope.defectiveDevices === 0) && $scope.defectiveDevices + incr >= 0) {
                $scope.defectiveDevices += incr;
                $http.put('/api/defectiveDevices', $scope.defectiveDevices);
            }
        };

        $http.get('/api/meanTemperature').then(function (res) {
            $scope.meanTemperature = parseFloat(res.data, 10);
        });
        $scope.updateMeanTemperature = function(incr) {
            if (($scope.meanTemperature || $scope.meanTemperature === 0) && $scope.meanTemperature + incr >= 0) {
                $scope.meanTemperature += incr;
                $http.put('/api/meanTemperature', $scope.meanTemperature);
            }
        };

        $http.get('/api/temperatureStdDev').then(function (res) {
            $scope.temperatureStdDev = parseFloat(res.data, 10);
        });
        $scope.updateTemperatureStdDev = function(incr) {
            if (($scope.temperatureStdDev || $scope.temperatureStdDev === 0) && $scope.temperatureStdDev + incr >= 0) {
                $scope.temperatureStdDev += incr;
                $http.put('/api/temperatureStdDev', $scope.temperatureStdDev);
            }
        };

        function updateAnalysis() {
            $http.get('/api/avg').then(function (res) {
                $scope.avg = res.data;
            });

            $http.get('/api/favg').then(function (res) {
                $scope.favg = res.data;
            });

            $http.get('/api/action').then(function (res) {
                $scope.action = res.data;
            });
        }

        updateAnalysis();
        setInterval(updateAnalysis, 2000);

    }]);
})(window.angular);