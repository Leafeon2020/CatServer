package org.spigotmc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.nio.file.StandardWatchEventKinds.*;
import java.io.File;
import java.nio.file.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Watchdog_Switch {
	private static final Logger log = LogManager.getLogger(Watchdog_Switch.class);
	public static boolean running;    //起動フラグ
	private static final String dir = new File(".").getAbsoluteFile().getParent();  //ディレクトリ取得
	private static final String file_name = dir + "/sleep_switch.txt";   //管理ファイル名取得

	public static void main(String[] args) throws Exception {
		//ファイル恒心監視起動
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Path path = Paths.get(dir);
		path.register(watchService, ENTRY_MODIFY);
		System.out.println(file_name + "を監視中");

		//以下ループ処理
		while (true) {
			if (!running) running = true;    //保険がてらコイツが落ちたらfalseにしたい
			WatchKey key = watchService.take(); //イベント発生までブロック
			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				Path changed = (Path) event.context();
				//恒心を確認
				if (kind == ENTRY_MODIFY && changed.toString().equals("sleep_switch.txt")) {
					System.out.println("watchdogの制御変更");
					try {
						//ファイル読み込み
						String fileContent = FileReader.readFileToString();
						if (fileContent == null) {
							System.out.println("ファイルの読み込みに失敗しました。");
						}
						else {
							//中身が0の場合watchdogの条件分岐から除外
							if (fileContent.equals("0")) {
								WatchdogThread.doStop();
								System.out.println("watchdog停止");
							}
							else {
								WatchdogThread.doStart(SpigotConfig.timeoutTime, SpigotConfig.restartOnCrash);
								System.out.println("watchdog起動");
							}
						}
					}
					//ファイルが無い時の例外
					catch (NullPointerException e) {
						log.error("ぬるぽ: ", e);
					}
				}
			}
			key.reset();    //再度待機状態に戻す
		}
	}

	//スイッチ内容読み込み
	public static class FileReader {
		public static String readFileToString() {
			try {
				byte[] fileBytes = Files.readAllBytes(Paths.get(file_name));
				return new String(fileBytes);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}