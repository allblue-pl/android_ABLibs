import java.net.NetworkInterface
import java.net.SocketException

extra["getLocalIP"] = fun(): String {
    try {
        for (ni in NetworkInterface.networkInterfaces()) {
            for (niAddress in ni.interfaceAddresses) {
                val ip = niAddress.address.hostAddress
                println("Checking network interface IP: $ip")

                if (ip.length <= 15 && !ip.equals("127.0.0.1") &&
                    ip.indexOf(":") == -1) {
                    println("Determined network interface IP: $ip")
                    return ip
                }
            }
        }
    } catch (e: SocketException) {
        println("Error getting local IP: " + e.message)
    }

    return "127.0.0.1"
}
