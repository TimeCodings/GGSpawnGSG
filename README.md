![80177](https://user-images.githubusercontent.com/94994775/209410008-3c452916-95b0-41c7-ac99-597154a62a90.png)
![CodeFactor](https://www.codefactor.io/repository/github/timecodings/ggspawngsg/badge)


**Spigot-Seite:** https://www.spigotmc.org/resources/griefergames-spawnplot-spawngs-plugin-100-einstellbar-api.80177/

**Hallo ;)**
ich heiße Diego und habe euch ein kleines aber feines Plugin programmiert, mit welchem ihr wie auf GrieferGames auch euren Spielern SpawnGS (se)/SpawnPlot (s) Gutscheine geben könnt, welche sie auf SpawnPlots (mit Command auswählbar) einlösen können. Außerdem könnt ihr alle Nachrichten, Permissions und mehr umstellen! Zudem gibt es ein API für Developer. Dieses Plugin ist kostenlos, was aber nicht heißt das der Support komplett wegfällt. (Bei Fehlern meldet euch auf Discord: TimeCode#0001)

**Bevor du loslegst:**
Bitte benutze bei Verwendung dieses Plugins folgende PlotSquared Versionen für folgende Minecraft Versionen:

**1.8.9 - 1.12.2** -> PlotSquared 3 (PlotSquared (intellectualsites.github.io))
**1.13 - 1.14** -> PlotSquared 4 (PlotSquared v4: v6 out now! | SpigotMC - High Performance Minecraft)
**1.13 - 1.19** -> PlotSquared 6 (Premium) (PlotSquared v6 | SpigotMC - High Performance Minecraft)

**Alle Restlichen PlotSquared-Versionen werden NICHT unterstützt**
**Zusatz:** Auch wenn das Plugin nur eine JAR-Datei ist, sollte diese alle 3 PlotSquared Versionen unterstützen!

**Einrichtung:**
1. Ziehe das Plugin auf deinen Server. Bitte achte darauf, dass du zusätzlich zum Plugin eine der folgenden PlotSquared-Versionen auf dem Server benötigst: v3, v4, v6
2. Restarte/Reloade anschließend den Server um Fehler zu vermeiden.
3. Das Plugin müsste nun laufen.... Mit /spawnplot help kannst du nun alle Commands einsehen!

*Was soll ich tun falls das Plugin nicht läuft oder Fehler auftreten?*
1. Überprüfe ob du die richtige PlotSquared Version heruntergeladen hast
2. Kontaktiere mich über den Diskussionen-Tab oder über Discord: TimeCode#0001 damit ich den Fehler schnellstmöglich beheben kann (ebenso bei Wünschen oder Anregungen bezüglich des Plugins)

**Commands:**
/spawnplot help | Listet alle Commands auf
/spawnplot add | Das aktuelle Plot worauf der Spieler steht wird zum SpawnPlot
/spawnplot remove | Das aktuelle Plot worauf der Spieler steht wird wieder zum normalen Plot
/spawnplot give <Spieler> | Falls das aktuelle Plot ein SpawnPlot ist wird dieses Plot den angegebenen Spieler überschrieben!
/spawnplot givevoucher <Spieler> <Anzahl> | Der angegebene Spieler erhält einen SpawnPlot Gutschein welchen er auf einem SpawnPlot mit Rechtsklick einlösen kann (TwoFactorClick kann auch eingestellt werden)
/spawnplot reload/rl | Die Konfigurationen werden neu geladen

**API:**
Ganz wichtig ist, das das Plugin in dem Build Path vorzufinden ist und außerdem beim testen auch auf den Testserver gezogen wird/wurde.

**Beispiel:**

    @EventHandler
    public void onRedeem(PlayerRedeemSpawnPlotEvent e) {
        //Wird ausgeführt wenn ein Spieler einen SpawnPlot gutschein einlöst
        e.setCancelled(true); //Funktion wird deaktiviert
        e.getPlayer().sendMessage("§a§lDiese Funktion ist momentan noch deaktiviert ;)");
    }
 
    @EventHandler
    public void onAdd(PlayerAddSpawnPlotEvent e) {
        //Wird ausgeführt wenn ein SpawnGS/SpawnPlot hiunzugefügt wird
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 2, 2);
    }
 
    @EventHandler
    public void onRemove(PlayerRemoveSpawnPlotEvent e) {
        //Wird ausgeführt wenn ein SpawnGS/SpawnPlot gelöscht wird
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_BASS, 2, 2);
    }
 
    @EventHandler
    public void onGiveVoucher(PlayerGiveVoucherEvent e) {
        //Wird ausgeführt wenn ein SpawnGS/SpawnPlot gutschein geholt wird
        Bukkit.getConsoleSender().sendMessage("§b" + e.getPlayer().getName()+" §ahat einen Gutschein eingelöst!");
    }
 
    @EventHandler
    public void onGive(PlayerGiveSpawnPlotEvent e) {
        //Wird ausgeführt wenn ein Spieler ein SpawnPlot mit /spawnplot give <Spieler> vergibt
        Bukkit.broadcastMessage("§aDer §cAdmin §ahat !");
    }
 
    public static ItemStack get() {
        //Man bekommt das SpawnGS/SpawnPlot Gutschein Item
        return SpawnGS.getVoucher();
    }
