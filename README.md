task for nexign bootcamp
# Задание

## Описание

Все звонки, совершенные абонентом сотового оператора, фиксируются в CDR формате.  
Когда абонент находится в роуминге, за процесс сбора данных отвечает обслуживающая сеть.  
Для стандартизации данных международная ассоциация GSMA ввела стандарт **BCE**.  
Согласно ему, данные из CDR агрегируются в отчет **UDR**, на основе которого оператор тарифицирует звонки.

Цель задания – **смоделировать этот процесс** в упрощенном виде.  
Микросервис должен:
- Генерировать CDR-записи.
- Сохранять их в базу данных (H2).
- Предоставлять REST API для получения UDR-отчетов.
- Формировать CDR-отчет по абоненту.

---

## Задача 1: Генерация CDR записей

### Условия:
1.	Во время генерации создаются CDR записи и сохраняются в локальную БД (h2);

2.	Данные генерируются в хронологическом порядке звонков, т.е. записи по одному абоненту могут прерываться записями по другому абоненту;

3.	Количество и длительность звонков определяется случайным образом;

4.	Установленный список абонентов (не менее 10) хранится в локальной БД (h2);

5.	Один прогон генерации создает записи сразу за 1 год.

### Формат CDR-записи:

тип вызова (01 - исходящие, 02 - входящие), номер инициатора, номер принимающего, дата и время начала (ISO 8601), дата и время окончания (ISO 8601)

## Задача 2: REST API для работы с UDR

### Условия:
1.  Требуется REST метод, который возвращает UDR запись (формат предоставлен выше) по одному переданному абоненту. В зависимости от переданных в метод параметров, UDR должен составляться либо за запрошенный месяц, либо за весь тарифицируемый период.

2.  Требуется REST метод, который возвращает UDR записи по всем нашим абонентам за запрошенный месяц.

3.  Данные можно брать только из БД.

### Формат UDR-объекта (JSON):

```json
{
    "msisdn": "79992221122",
    "incomingCall": {
        "totalTime": "02:12:13"
    },
    "outcomingCall": {
        "totalTime": "00:02:50"
    }
}
```

## Задача 3 (Дополнительная): Формирование CDR-отчета

### Условия:
Условия:

1.	REST-метод для генерации CDR-отчета.
Возвращает UUID запроса и статус выполнения.

2.	Отчет создается за переданный период (не обязательно календарный месяц).
Можно запросить отчет за две недели, полгода и т. д..

3.	Данные берутся только из БД.
Файл сохраняется в директории /reports в формате CSV или TXT.
Название файла содержит номер пользователя и UUID запроса.