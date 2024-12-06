import java.util.*;

public class apresentacao {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Map<Integer, Map<Integer, List<String>>>> calendario = new HashMap<>();
    private static Set<String> mesesValidos = new HashSet<>(Arrays.asList(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"));
        
    //Mapa dos meses e dias
    private static Map<String, Integer> diasPorMes = new HashMap<>();
    static {
        diasPorMes.put("Janeiro", 31);
        diasPorMes.put("Fevereiro", 28);
        diasPorMes.put("Março", 31);
        diasPorMes.put("Abril", 30);
        diasPorMes.put("Maio", 31);
        diasPorMes.put("Junho", 30);
        diasPorMes.put("Julho", 31);
        diasPorMes.put("Agosto", 31);
        diasPorMes.put("Setembro", 30);
        diasPorMes.put("Outubro", 31);
        diasPorMes.put("Novembro", 30);
        diasPorMes.put("Dezembro", 31);
    }
    
    //Interface
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Adicionar Evento");
            System.out.println("2. Exibir Calendário");
            System.out.println("3. Exibir Eventos de um Mês");
            System.out.println("4. Excluir Evento");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarEvento();
                    break;
                case 2:
                    exibirCalendario();
                    break;
                case 3:
                    exibirEventosDeUmMes();
                    break;
                case 4:
                    excluirEvento();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    
    //Opções
    private static void adicionarEvento() {
        String mes = obterMesValido();
        int dia = obterDiaValido(mes);
        int hora = obterHoraValida();

        System.out.print("Digite a descrição do evento: ");
        String descricao = scanner.nextLine();

        calendario.computeIfAbsent(mes, k -> new HashMap<>())
                  .computeIfAbsent(dia, k -> new HashMap<>())
                  .computeIfAbsent(hora, k -> new ArrayList<>())
                  .add(descricao);
        System.out.println("Evento adicionado com sucesso para o dia " + dia + " de " + mes + " às " + hora + " horas!");
    }

    private static String obterMesValido() {
        String mes;
        while (true) {
            System.out.print("Digite o mês do evento (ex: Janeiro): ");
            mes = scanner.nextLine();

            if (mesesValidos.contains(mes)) {
                break;
            } else {
                System.out.println("Mês inválido. Tente novamente.");
            }
        }
        return mes;
    }

    private static int obterDiaValido(String mes) {
        int dia;
        while (true) {
            //Ajustar para anos bissextos
            if (mes.equals("Fevereiro")) {
                System.out.print("Digite o dia do evento (1 a " + (diasPorMes.get(mes) + (anoBissexto() ? 1 : 0)) + "): ");
            } else {
                System.out.print("Digite o dia do evento (1 a " + diasPorMes.get(mes) + "): ");
            }
            dia = scanner.nextInt();
            scanner.nextLine();

            if (dia >= 1 && dia <= diasPorMes.get(mes)) {
                break;
            } else {
                System.out.println("Dia inválido para o mês de " + mes + ". Tente novamente.");
            }
        }
        return dia;
    }

    //Hora do evento
    private static int obterHoraValida() {
        int hora;
        while (true) {
            System.out.print("Digite a hora do evento (0 a 23): ");
            hora = scanner.nextInt();
            scanner.nextLine();

            if (hora >= 0 && hora <= 23) {
                break;
            } else {
                System.out.println("Hora inválida. Tente novamente.");
            }
        }
        return hora;
    }

    //Verificar ano bissexto
    private static boolean anoBissexto() {
        int ano;
        System.out.print("Digite o ano (ex: 2024): ");
        ano = scanner.nextInt();
        scanner.nextLine();

        return (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0));
    }
    
    //Lista de eventos (gerais)
    private static void exibirCalendario() {
        if (calendario.isEmpty()) {
            System.out.println("Nenhum evento registrado.");
        } else {
            for (Map.Entry<String, Map<Integer, Map<Integer, List<String>>>> entryMes : calendario.entrySet()) {
                String mes = entryMes.getKey();
                System.out.println("\nEventos de " + mes + ":");
                for (Map.Entry<Integer, Map<Integer, List<String>>> entryDia : entryMes.getValue().entrySet()) {
                    int dia = entryDia.getKey();
                    for (Map.Entry<Integer, List<String>> entryHora : entryDia.getValue().entrySet()) {
                        int hora = entryHora.getKey();
                        List<String> eventos = entryHora.getValue();
                        for (String evento : eventos) {
                            System.out.println("Dia " + dia + " às " + hora + "h: " + evento);
                        }
                    }
                }
            }
        }
    }
    
    //Exibir eventos (por mês)
    private static void exibirEventosDeUmMes() {
        System.out.print("Digite o mês para exibir os eventos (ex: Janeiro): ");
        String mes = scanner.nextLine();

        Map<Integer, Map<Integer, List<String>>> eventosDoMes = calendario.get(mes);
        if (eventosDoMes != null && !eventosDoMes.isEmpty()) {
            System.out.println("\nEventos de " + mes + ":");
            for (Map.Entry<Integer, Map<Integer, List<String>>> entryDia : eventosDoMes.entrySet()) {
                int dia = entryDia.getKey();
                for (Map.Entry<Integer, List<String>> entryHora : entryDia.getValue().entrySet()) {
                    int hora = entryHora.getKey();
                    List<String> eventos = entryHora.getValue();
                    for (String evento : eventos) {
                        System.out.println("Dia " + dia + " às " + hora + "h: " + evento);
                    }
                }
            }
        } else {
            System.out.println("Não há eventos para o mês de " + mes + ".");
        }
    }
    
    //Excluir evento
    private static void excluirEvento() {
    System.out.print("Digite o mês do evento que deseja excluir (ex: Janeiro): ");
    String mes = scanner.nextLine();

    Map<Integer, Map<Integer, List<String>>> eventosDoMes = calendario.get(mes);
    if (eventosDoMes != null && !eventosDoMes.isEmpty()) {
        System.out.print("Digite o dia do evento que deseja excluir: ");
        int dia = scanner.nextInt();
        scanner.nextLine();

        Map<Integer, List<String>> eventosDoDia = eventosDoMes.get(dia);
        if (eventosDoDia != null && !eventosDoDia.isEmpty()) {
            System.out.println("Eventos disponíveis no dia " + dia + " em " + mes + ":");
            for (int hora : eventosDoDia.keySet()) {
                List<String> eventosDaHora = eventosDoDia.get(hora);
                for (int i = 0; i < eventosDaHora.size(); i++) {
                    System.out.println((i + 1) + ". " + eventosDaHora.get(i));
                }
            }

            System.out.print("Digite o número do evento que deseja excluir: ");
            int indice = scanner.nextInt() - 1;
            scanner.nextLine();

            //Encontrar e remover o evento
            boolean eventoExcluido = false;
            for (Map.Entry<Integer, List<String>> entryHora : eventosDoDia.entrySet()) {
                List<String> eventosDaHora = entryHora.getValue();
                if (indice >= 0 && indice < eventosDaHora.size()) {
                    eventosDaHora.remove(indice);
                    eventoExcluido = true;
                    System.out.println("Evento excluído com sucesso!");
                    break;
                }
            }

            if (!eventoExcluido) {
                System.out.println("Número de evento inválido.");
            }
        } else {
            System.out.println("Não há eventos para o dia " + dia + " no mês de " + mes + ".");
        }
    } else {
        System.out.println("Não há eventos para o mês de " + mes + ".");

    }
}
}