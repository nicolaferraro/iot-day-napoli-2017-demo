(function(angular) {
    'use strict';
    var myApp = angular.module('Iot', []);

    myApp.controller('IotController', ['$scope', '$http', function($scope, $http) {

        $scope.devices = 1;
        $scope.updateDevices = function(incr) {
            if ($scope.devices + incr >= 0) {
                $scope.devices += incr;
                $http.put('/api/devices', $scope.devices);
            }
        };

        $scope.defectiveDevices = 0;
        $scope.updateDefectiveDevices = function(incr) {
            if ($scope.defectiveDevices + incr >= 0) {
                $scope.defectiveDevices += incr;
                $http.put('/api/defectiveDevices', $scope.defectiveDevices);
            }
        };

        $scope.meanTemperature = 20.0;
        $scope.updateMeanTemperature = function(incr) {
            if ($scope.meanTemperature + incr >= 0) {
                $scope.meanTemperature += incr;
                $http.put('/api/meanTemperature', $scope.meanTemperature);
            }
        };

        $scope.temperatureStdDev = 2.0;
        $scope.updateTemperatureStdDev = function(incr) {
            if ($scope.temperatureStdDev + incr >= 0) {
                $scope.temperatureStdDev += incr;
                $http.put('/api/temperatureStdDev', $scope.temperatureStdDev);
            }
        };

        function updateAnalysis() {
            $http.get('/api/avg').then(function (res) {
                $scope.avg = res.data;
            }, function(err) {
                // not yet available
            });
        }

        updateAnalysis();
        setInterval(updateAnalysis, 3000);

    }]);
})(window.angular);