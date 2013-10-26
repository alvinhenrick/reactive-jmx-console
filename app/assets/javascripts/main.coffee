'use strict';


require.config
  paths:
    angular: "/webjars/angularjs/1.1.5/angular",
    nvd3: "vendors/nvd3/angularjs-nvd3-directives"

  shim:
    angular:
      exports: "angular"

    nvd3:
      deps: ["angular"]

  priority: ["angular"]


require ["angular", "app", "nvd3", "./controllers", "./routes", "./directives", "./filters", "./services" ,"./factories"], (angular) ->
  angular.bootstrap(document, ["jmxconsole"])
