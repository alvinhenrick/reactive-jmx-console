'use strict';


require.config
  paths:
    angular: "/webjars/angularjs/1.2.12/angular",
    angularroute: "/webjars/angularjs/1.2.12/angular-route",
    nvd3: "../assets/lib/nvd3/angularjs-nvd3-directives"

  shim:
    angular:
      exports: "angular"

    nvd3:
      deps: ["angular"]

    angularroute:
      deps: ["angular"]

  priority: ["angular"]


require ["angular","angularroute","nvd3", "app", "controllers", "routes", "directives", "filters", "services" ,"factories"], (angular) ->
  angular.bootstrap(document, ["jmxconsole"])
