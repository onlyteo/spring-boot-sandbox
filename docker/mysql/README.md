# MySQL

## Enable CDC
In order to allow CDC in MySQL it is necessary to enable binary logging of transaction updates.
This is done by using the following CLI parameters:

```bash
--lower_case_table_names=1 --log-bin=bin.log --log-bin-index=bin.log.index --binlog_do_db=sandbox
```

| Parameter              | Description                                                   |
|------------------------|---------------------------------------------------------------|
| lower_case_table_names | Forcing lower case table names so topics also have lower case |
| log-bin                | Name of binary log file                                       |
| log-bin-index          | Name of binary log index file                                 |
| binlog_do_db           | Name of the databases which should enable binary logging      |
