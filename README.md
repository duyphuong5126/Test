# About HomeTest app
- Used third party libraries: 
  + Retrofit 2: 2.4.0 (included Gson Converter)
  + OkHttp 3 Logging Interceptor: 3.9.0
  + Kotlin Coroutines: 0.24.0
  + Android persistence Room database: 1.1.1
  + Databinding: 3.2.0
  
- String processing algorithm: 
Start from the center of the string, if
  + The center element is white space, replacing it by the new line character then return the new string.
  + If not, using 2 pointers to traverse from the center to the head/tail of the string. The pointer that meets the white space first will replace that white space with the new line character then return the new string.
    + If there is no white space caught by the pointers, return the original string.
