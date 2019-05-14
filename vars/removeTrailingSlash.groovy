
String call(myString) {
    if (myString.endsWith("/")) {
        return myString.substring(0, myString.length() - 1)
    }
    return myString
}