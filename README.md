# A1

#1 - "IP-адреса"
1. Выбрать перевод:
    1) int->IpV4
    2) IpV4->int 
   
2. ввести строчку(пример "0.0.0.255") или число
3. вывод результата

#2 - "Стремится к нулю или к бесконечности?"
1. Ввести n
2. вывод результата

#3 - "Неавторизованные поставки"
Java SpringBoot, Postgresql, freemarker
1. Сервер запускается по пути http://localhost:8080/
2. при переходе на сайт, парсится logins.csv, postings.csv и сохраняется в БД после чего выводится в виде таблицы
3. есть сортировка по заданным значениям в самом начале страницы
4. для Rest http://localhost:8080/api (Дата с - dateStart(yyyy-dd-MM), дата по = dateEnd(yyyy-dd-MM), авторизованная поставка = select(true,false))
    Пример запросов
    1) http://localhost:8080/api?select=false
    2) http://localhost:8080/api?dateStart=2019-08-08&dateEnd=2020-08-08&select=false
