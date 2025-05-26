# CatServer (1.12.2)
![](https://img.shields.io/badge/Minecraft-1.12.2-brightgreen.svg?colorB=469C00)
![](https://img.shields.io/badge/Forge-14.23.5.2860-brightgreen.svg?colorB=469C00)
![](https://img.shields.io/badge/Spigot-1.12.2-brightgreen.svg?colorB=469C00)

## 概要
これは[Luohuayu師のCatServer](https://github.com/Luohuayu/CatServer)にOSのスリープモードを使えるように改造を施した物になります。  
基本的に[自作のDiscordのBOT](https://github.com/Leafeon2020/Server-Automatic-Power-Management-Tool)とセットで運用する前提の設計となっていますがプロセス間通信にファイルを使っているので別の方法でも制御は可能です。

※**他バージョンのソースコードは一切触っていないため1.12.2ブランチ以外はフォーク元そのまんまです。**

## 改造を施した箇所
[Watchdog_Switch.java](../src/main/java/org/spigotmc/Watchdog_Switch.java)と[WatchdogThread.java](../src/main/java/org/spigotmc/WatchdogThread.java)の2個所だけです。他は触っていません。  
前者は文字通りWatchdogの制御スイッチになっています。後者はスイッチを呼び出す改造のみ施しています。

## 挙動
カレントディレクトリにあるsleep_switch.txtの中身を見ます。  
0ならWatcdogを無効化してそれ以外なら有効化する作りです。

## 移植について
多分Javaのバージョン依存が強い書き方だったりMinecraftのバージョンに依存しない記述になっているため他のSpigot系サーバーに移植する事は可能だと思います。

## ライセンス
![LGPLv3](https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/LGPLv3_Logo.svg/640px-LGPLv3_Logo.svg.png)
