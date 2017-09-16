# XMLCalculator
## Сборка
`mvn install`
## Запуск
`java -jar target/calculator.jar путь/к/исходному_файлу.xml [путь/к/файлу_результата.xml]`   
Если файл назначения не указан, тогда будет создан файл target.xml в той же директории, что и исходный файл
## Описание
Программа:
* Принимает на вход xml файл, содержащий исходные данные для расчетов
* Проверяет его на валидность схеме Calculator.xsd (файл содержится в jar, поэтому его не обязательно располагать рядом с xml)
* Выполняет расчеты
* Сохраняет результаты в новый xml файл, соответствующий той же схеме

При наличии таких ошибок, как несоответствие xml файла схеме или некорректный путь к xml файлу, программа выведет соответствующее сообщение и прекратит работу.
