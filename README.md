# SecurePlan

Android-app för projektering och dokumentation av säkerhetssystem (larm, CCTV, passagesystem, dörrbestyckning).

---

## Kom igång

### 1. Ladda ner Android Studio

Gå till [developer.android.com/studio](https://developer.android.com/studio) och ladda ner senaste versionen av Android Studio. Installera och starta programmet.

### 2. Hämta projektet från GitHub

1. Öppna Android Studio
2. På välkomstskärmen, välj **File → New → Project from Version Control**
3. Klistra in repo-URL:en:
   ```
   https://github.com/fredlange/SecurePlan.git
   ```
4. Välj var du vill spara projektet och klicka **Clone**
5. Vänta tills Android Studio laddat klart och Gradle sync är klar (kan ta någon minut)

### 3. Starta Gemini i Android Studio

Gemini är Googles AI-assistent inbyggd i Android Studio och hjälper dig förstå och bygga vidare på koden.

1. Klicka på **Gemini**-ikonen i höger sidofält (stjärnsymbolen ✦), eller välj **View → Tool Windows → Gemini**
2. Logga in med ditt Google-konto om du inte redan är inloggad
3. Nu kan du ställa frågor om koden direkt i editorn, t.ex. *"Hur lägger jag till en ny symbol?"*

### 4. Kör appen

Tryck på den gröna **Run**-knappen ▶ när du valt en enhet (se alternativen nedan).

---

#### Alternativ A — Emulator (ingen fysisk enhet behövs)

1. Välj **Device Manager** i höger sidofält (telefon-ikonen)
2. Klicka **+** → **Create Virtual Device**
3. Välj en telefon, t.ex. *Pixel 8*, och klicka **Next**
4. Välj ett system-image med **API 34** eller senare — ladda ner det om det saknas
5. Klicka **Finish** och starta emulatorn med ▶ i Device Manager

---

#### Alternativ B — Trådlöst via WiFi (kör på din egen telefon)

> Telefonen och datorn måste vara på **samma WiFi-nätverk**.

1. Aktivera **Utvecklaralternativ** på din Android-enhet:
   - Gå till *Inställningar → Om telefonen*
   - Tryck **7 gånger** på *Versionsnummer* tills du ser *"Du är nu en utvecklare"*
2. Gå till *Inställningar → Utvecklaralternativ* och aktivera **Trådlös felsökning** (Wireless debugging)
3. Öppna Wireless debugging och välj **Para ihop enhet med QR-kod**
4. I Android Studio, öppna **Running Devices** → **+** → **Pair Devices Using Wi-Fi**
5. Skanna QR-koden som visas i Android Studio med telefonen
6. Enheten dyker upp i Run-menyn — välj den och tryck ▶

---

## Felsökning — vanliga Gradle-problem

> Du behöver inte installera Gradle eller Kotlin manuellt. Android Studio sköter allt — men det kan gå fel om något saknas eller är felinställt.

---

### "Gradle sync failed" direkt efter clone

**Orsak:** Android Studio behöver ladda ner Gradle och alla beroenden första gången, vilket kräver internetanslutning.

**Lösning:**
1. Kontrollera att du har internetåtkomst
2. Välj **File → Sync Project with Gradle Files**
3. Vänta tills statusfältet längst ner visar *"Gradle sync finished"*

---

### "SDK location not found" eller "No Android SDK found"

**Orsak:** Android SDK är inte installerat eller hittas inte.

**Lösning:**
1. Gå till **File → Settings** (Windows/Linux) eller **Android Studio → Settings** (Mac)
2. Välj **Appearance & Behavior → System Settings → Android SDK**
3. Klicka **Edit** bredvid SDK-sökvägen och låt Android Studio installera SDK:n automatiskt

---

### "JDK not found" eller fel Java-version

**Orsak:** Android Studio behöver JDK 17 eller senare. Det medföljer normalt, men kan saknas.

**Lösning:**
1. Gå till **File → Settings → Build, Execution, Deployment → Build Tools → Gradle**
2. Under **Gradle JDK**, välj **Embedded JDK** (den som följer med Android Studio)
3. Gör om Gradle sync

---

### Gradle sync hänger sig eller tar för lång tid

**Lösning:**
1. Stäng Android Studio helt
2. Radera mappen `.gradle` i projektets rotmapp (den är återskapsbar)
3. Öppna projektet igen — Android Studio laddar ner allt på nytt

---

### "Could not resolve" — beroenden kan inte laddas ner

**Orsak:** Brandvägg, proxy eller tillfälligt nätverksproblem.

**Lösning:**
- Prova igen om en stund
- Om du är på ett företagsnätverk, kontrollera att Maven Central (`repo.maven.apache.org`) och Google (`dl.google.com`) inte är blockerade
- Prova med ett vanligt hemmanätverk eller mobil hotspot

---

### Appen byggs men kraschar vid start på emulatorn

**Orsak:** Databasen i appen uppdaterades — gamla data är inkompatibel.

**Lösning:** Avinstallera appen från emulatorn och kör igen:
- Högerklicka på appikonen i emulatorn → **Uninstall**, eller kör i terminalen:
  ```
  adb uninstall se.secureplan.app
  ```
