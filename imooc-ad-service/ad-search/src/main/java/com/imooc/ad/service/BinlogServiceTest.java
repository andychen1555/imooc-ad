package com.imooc.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

public class BinlogServiceTest {

  public static void main(String[] args) throws Exception{
    BinaryLogClient client = new BinaryLogClient(
        "127.0.0.1",
        3306,
        "root",
        "123"
    );
    // 可以设定读取 Binlog 的文件和位置，那么，client 将从这个位置之后开始监听
    // 否则, client 会从 “头” 开始读取 Binlog 文件，并监听
//        client.setBinlogFilename("binlog.000037");
//        client.setBinlogPosition();
    client.registerEventListener(event -> {
         EventData data = event.getData();
         if (data instanceof UpdateRowsEventData) {
           System.out.println("Update--------------");
           System.out.println(data.toString());
         } else if (data instanceof WriteRowsEventData) {
           System.out.println("Write---------------");
           System.out.println(data.toString());
         } else if (data instanceof DeleteRowsEventData) {
           System.out.println("Delete--------------");
           System.out.println(data.toString());
         }
       }
    );

    client.connect();
  }
}
