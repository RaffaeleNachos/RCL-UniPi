# RCL-UniPi
Assignments Laboratorio Reti @ UniPi 19/20 (Java)

### Assegnamento 1
Scrivere un programma che attiva un thread T che effettua il calcolo approssimato di Pigreco. Il programma principale riceve in input da linea di comando un parametro che indica il grado di accuratezza (accuracy) per il calcolo di Pigreco ed il tempo massimo di attesa dopo cui il programma principale interompe il thread T. Il thread T effettua un ciclo infinito per il calcolo di Pigreco usando la serie di Gregory-Leibniz ( pi = 4/1 – 4/3 + 4/5 - 4/7 + 4/9 - 4/11 ...).
Il thread esce dal ciclo quando una delle due condizioni seguenti risulta verificata:
1) il thread è stato interrotto
2) la differenza tra il valore stimato di pi ed il valore Math.PI (della libreria JAVA) è minore di accuracy

### Assegnamento 2
Simulare il flusso di clienti in un ufficio postale che ha 4 sportelli. Nell'ufficio esiste:
- un'ampia sala d'attesa in cui ogni persona può entrare liberamente. Quando entra, ogni persona prende il numero dalla numeratrice e aspetta il proprio turno in questa sala.
- una seconda sala, meno ampia, posta davanti agli sportelli, in cui si può entrare solo a gruppi di k persone
Una persona si mette quindi prima in coda nella prima sala, poi passa nella seconda sala.
Ogni persona impiega un tempo differente per la propria operazione allo sportello. Una volta terminata l'operazione, la persona esce dall'ufficio
Scrivere un programma in cui:
- l'ufficio viene modellato come una classe JAVA, in cui viene attivato un ThreadPool di dimensione uguale al numero degli sportelli
- la coda delle persone presenti nella sala d'attesa è gestita esplicitamente dal programma
- la seconda coda (davanti agli sportelli) è quella gestita implicitamente dal
ThreadPool
- ogni persona viene modellata come un task, un task che deve essere assegnato ad uno dei thread associati agli sportelli
- si preveda di far entrare tutte le persone nell'ufficio postale, all'inizio del programma
Facoltativo: prevedere il caso di un flusso continuo di clienti e la possibilità che l'operatore chiuda lo sportello stesso dopo che in un certo intervallo di tempo non si presentano clienti al suo sportello.

### Assegnamento 3
Il Reparto di Ortopedia dell’ospedale di Marina è gestito da una equipe di 10 medici, numerati da 1 a 10. I pazienti che accedono al reparto devono essere visitati dai medici e sono distinti in base ad un codice di gravità/urgenza della prestazione:
- Codice rosso: hanno priorità su tutti e la loro visita richiede l’intervento esclusivo di tutti i medici dell’equipe.
- Codice giallo: la visita richiede l’intervento esclusivo di un solo medico, identificato dall’indice i, perchè quel medico ha una particolare specializzazione
- Codice bianco: la visita richiede l’intervento esclusivo di un qualsiasi medico
il codice rosso ha priorità su tutti nell'accesso alle risorse del reparto, il codice giallo ha priorità sul codice bianco.
Nessuna visita può essere interrotta, il gestore del reparto deve coordinare il lavoro dei medici (i.e. l’accesso dei pazienti alle visite). scrivere un programma JAVA che simuli il comportamento dei pazienti e del gestore del reparto.
- il programma riceve in ingresso il numero di pazienti in codice bianco, giallo, rosso ed attiva un thread per ogni paziente.
- ogni utente accede k volte al reparto per effettuare la visita, con k generato casualmente. Simulare l'intervallo di tempo che intercorre tra un accesso ed il successivo e l'intervallo di permanenza in visita mediante il metodo sleep.
- il programma deve terminare quando tutti i pazienti utenti hanno completato le visite in reparto.

### Assegnamento 4
si scriva un programma JAVA che
- riceve in input un filepath che individua una directory D
- stampa le informazioni del contenuto di quella directory e, ricorsivamente,
di tutti i file contenuti nelle sottodirectory di D
il programma deve essere strutturato come segue:
- attiva un thread produttore ed un insieme di k thread consumatori il produttore comunica con i consumatori mediante una coda
- il produttore visita ricorsivamente la directory data ed, eventualmente tutte le sottodirectory e mette nella coda il nome di ogni directory individuata
- i consumatori prelevano dalla coda i nomi delle directories e stampano il loro contenuto
- la coda deve essere realizzata con una LinkedList. Ricordiamo che una Linked List non è una struttura thread-safe.

### Assegnamento 5
creare un file contenente oggetti che rappresentano i conti correnti di una banca. Ogni conto corrente contiene il nome del correntista ed una lista di movimenti. I movimenti registrati per un conto corrente sono relativi agli ultimi 2 anni, quindi possono essere molto numerosi.
- per ogni movimento vengono registrati la data e la causale del movimento. L'insieme delle causali possibili è fissato: Bonfico, Accredito, Bollettino, F24, PagoBancomat.
- rileggere il file e trovare, per ogni possibile causale, quanti movimenti hanno quella causale.
- progettare un'applicazione che attiva un insieme di thread. Uno di essi legge dal file gli oggetti “conto corrente” e li passa, uno per volta, ai thread presenti in un thread pool.
- ogni thread calcola il numero di occorrenze di ogni possibile causale all'interno di quel conto corrente ed aggiorna un contatore globale.
- alla fine il programma stampa per ogni possibile causale il numero totale di occorrenze.
- utilizzare NIO per l'interazione con il file e JSON per la serializzazione

### Assegnamento 6
scrivere un programma JAVA che implementi un server Http che gestisca richieste di trasferimento di file di diverso tipo (es. immagini jpeg, gif) provenienti da un browser web.
il server:
- sta in ascolto su una porta nota al client (es. 6789)
- gestisce richieste Http di tipo GET alla Request URL localhost:port/filename
- le connessioni possono essere non persistenti.
- usare le classi Socket e ServerSocket per sviluppare il programma server
- per inviare al server le richieste, utilizzare un qualsiasi browser

### Assegnamento 7
Scrivere un programma echo server usando la libreria java NIO e, in particolare, il Selector e canali in modalità non bloccante, e un programma echo client, usando NIO (va bene anche con modalità bloccante).
Il server accetta richieste di connessioni dai client, riceve messaggi inviati dai client e li rispedisce (eventualmente aggiungendo "echoed by server" al messaggio ricevuto).
Il client legge il messaggio da inviare da console, lo invia al server e visualizza quanto ricevuto dal server.

### Assegnamento 8
PING CLIENT:
- accetta due argomenti da linea di comando: nome e porta del server. Se uno o più argomenti risultano scorretti, il client termina, dopo aver stampato un messaggio di errore del tipo ERR -arg x, dove x è il numero dell'argomento.
- utilizza una comunicazione UDP per comunicare con il server ed invia 10 messaggi al server, con il seguente formato:
*PING seqno timestamp*
in cui seqno è il numero di sequenza del PING (tra 0-9) ed il timestamp (in millisecondi) indica quando il messaggio è stato inviato
- non invia un nuovo PING fino che non ha ricevuto l'eco del PING precedente, oppure è scaduto un timeout.
stampa ogni messaggio spedito al server ed il RTT del ping oppure un * se la risposta non è stata ricevuta entro 2 secondi
- dopo che ha ricevuto la decima risposta (o dopo il suo timeout), il client stampa un riassunto simile a quello stampato dal PING UNIX
---- PING Statistics ----
10 packets transmitted, 7 packets received, 30% packet loss round-trip (ms) min/avg/max = 63/190.29/290
- il RTT medio è stampato con 2 cifre dopo la virgola

PING SERVER:
- accetta un argomento da linea di comando: la porta, che è quella su cui è attivo il server + un argomento opzionale, il seed, un valore long utilizzato per la generazione di latenze e perdita di pacchetti. Se uno qualunque degli argomenti è scorretto, stampa un messaggio di errore del tipo ERR -arg x, dove x è il numero dell'argomento.
- dopo aver ricevuto un PING, il server determina se ignorare il pacchetto (simulandone la perdita) o effettuarne l'eco. La probabilità di perdita di pacchetti di default è del 25%.
- se decide di effettuare l'eco del PING, il server attende un intervallo di tempo casuale per simulare la latenza di rete
- stampa l'indirizzo IP e la porta del client, il messaggio di PING e l'azione intrapresa dal server in seguito alla sua ricezione (PING non inviato,oppure PING ritardato di x ms).

### Assegnamento 9
Si progetti un’applicazione Client/Server per la gestione delle registrazioni ad un congresso. L’organizzazione del congresso fornisce agli speaker delle varie sessioni un’interfaccia tramite la quale iscriversi ad una sessione, e la possibilità di visionare i programmi delle varie giornate del congresso, con gli interventi delle varie sessioni.

Il server mantiene i programmi delle 3 giornate del congresso, ciascuno dei quali è memorizzato in una struttura dati come quella mostrata di seguito, in cui ad ogni riga corrisponde una sessione (in tutto 12 per ogni giornata). Per ciascuna sessione vengono memorizzati i nomi degli speaker che si sono registrati (al massimo 5).

Il client può richiedere operazioni per:
- registrare uno speaker ad una sessione;
- ottenere il programma del congresso;

Il client inoltra le richieste al server tramite il meccanismo di RMI. Prevedere, per ogni possibile operazione una gestione di eventuali condizioni anomale (ad esempio la richiesta di registrazione ad una giornata e/o sessione inesistente oppure per la quale sono già stati coperti tutti gli spazi d’intervento)

Il client è implementato come un processo ciclico che continua a fare richieste sincrone fino ad esaurire tutte le esigenze utente. Stabilire una opportuna condizione di terminazione del processo di richiesta. Client, server e registry possono essere eseguiti sullo stesso host.

### Assegnamento 10
Programmare un Server TimeServer, che invia su un gruppo di multicast "dategroup", ad intervalli regolari, la data e l’ora. 
(attende tra un invio ed il successivo un intervallo di tempo simulata mediante il metodo sleep( )). 
L’indirizzo IP di dategroup viene introdotto da linea di comando. Definire quindi un client TimeClient che si unisce a dategroup e riceve, per dieci volte consecutive, data ed ora, le visualizza, quindi termina.
