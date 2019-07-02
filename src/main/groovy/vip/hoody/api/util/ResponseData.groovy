package vip.hoody.api.util

class ResponseData {

    def data

    Integer count

    boolean success = true

    String msg


    @Override
    public String toString() {
        return "ResponseData{" +
                "data=" + data +
                ", count=" + count +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
