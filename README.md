Demo app designed to display basic information about chosen city and recommend local activities based on user interests.

In order to build the app you must:
1. Define your GOOGLE_PLACES_API_KEY constant in local.properties.
2. Provide config files for identity providers in JSON format to the path ./Polis/app/src/main/res/raw/
Example files:
```
{
  "authorizationUri": "https://accounts.google.com/o/oauth2/v2/auth?prompt=login",
  "tokenUri": "https://www.googleapis.com/oauth2/v4/token",
  "clientId": "[your client id]",
  "clientSecret": "[your client secret]",
  "permissionScope": "email",
  "codeChallengeMethod": "S256",
  "messageDigestAlgorithm": "SHA-256"
}
```
3. Change redirect URI in the app manifest and in the ./Polis/app/src/main/java/com/avangarde/polis/configuration/Idp.kt file.

**Notes:**
Login redirect was tested and currently works only in firefox browser.
