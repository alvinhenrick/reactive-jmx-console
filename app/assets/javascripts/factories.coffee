'use strict'

class SocketService
  constructor: ($log) ->
    service = {}

    service.connect = ->
      return  if service.ws
      ws = new WebSocket("ws://localhost:9000/indexWS")

      ws.onopen = ->
        service.callback "Succeeded"
        return

      ws.onerror = ->
        service.callback "Failed to open a connection"
        return

      ws.onmessage = (message) ->
        service.callback message.data
        return

      service.ws = ws

    service.send = (message) ->
      service.ws.send message
      return

    service.subscribe = (callback) ->
      service.callback = callback
      return

    return service


define ['app'], (app) -> app.factory 'SocketService', ['$log', SocketService]
