'use strict'

class MyCtrl1
  constructor: ($log) ->
    $log.info 'MyCtrl1'

class MyCtrl2
  constructor: ($log) ->
    $log.info 'MyCtrl2'

class ExampleCtrl
  constructor:($log,$scope,SocketService) ->
    $scope.exampleData = [
      key: "Github Api Mean Web Response Time"
      values: [[1378387200.0, 123.08370666666667], [1378387500.0, 119.64371999999999], [1378387800.0, 126.92131333333332], [1378388100.0, 122.06958666666667], [1378388400.0, 126.50453], [1378388700.0, 168.14301666666668], [1378389000.0, 132.83243], [1378389300.0, 137.11919333333336], [1378389600.0, 152.85155], [1378389900.0, 133.26816], [1378390200.0, 178.5094466666667], [1378390500.0, 156.0947666666667]]
    ]

    $scope.xAxisTicksFunction = ->
      $log.info "xAxisTicksFunction"
      $log.info d3.svg.axis().ticks(d3.time.minutes, 5)
      (d) -> d3.svg.axis().ticks d3.time.minutes, 5

    $scope.xAxisTickFormatFunction = ->
      (d) -> d3.time.format("%H:%M") moment.unix(d).toDate()

    SocketService.connect()

    SocketService.subscribe (message) -> $log.info message



define ['app'], (app) -> app.controller('MyCtrl1', ['$log', MyCtrl1])
                            .controller('MyCtrl2', ['$log', MyCtrl2])
                            .controller('ExampleCtrl',['$log','$scope','SocketService',ExampleCtrl])


