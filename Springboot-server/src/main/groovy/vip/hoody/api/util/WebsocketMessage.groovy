package vip.hoody.api.util

class WebsocketMessage {

    def data

    String destination

    def getData() {
        return data
    }

    void setData(data) {
        this.data = data
    }

    String getDestination() {
        return destination
    }

    void setDestination(String destination) {
        this.destination = destination
    }
}
