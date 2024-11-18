import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, List<String>> calendario = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Adicionar Evento");
            System.out.println("2. Exibir Calendário");
            System.out.println("3. Exibir Eventos de um Mês");
            System.out.println("4. Sair");
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
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

  
    private static void adicionarEvento() {
        System.out.print("Digite o mês do evento (ex: Janeiro): ");
        String mes = scanner.nextLine();

        System.out.print("Digite a descrição do evento: ");
        String descricao = scanner.nextLine();

        calendario.computeIfAbsent(mes, k -> new ArrayList<>()).add(descricao);
        System.out.println("Evento adicionado com sucesso!");
    }

   
    private static void exibirCalendario() {
        if (calendario.isEmpty()) {
            System.out.println("Nenhum evento registrado.");
        } else {
            for (Map.Entry<String, List<String>> entry : calendario.entrySet()) {
                System.out.println("\nEventos de " + entry.getKey() + ":");
                for (String evento : entry.getValue()) {
                    System.out.println("- " + evento);
                }
            }
        }
    }

  
    private static void exibirEventosDeUmMes() {
        System.out.print("Digite o mês para exibir os eventos (ex: Janeiro): ");
        String mes = scanner.nextLine();

        List<String> eventos = calendario.get(mes);
        if (eventos != null && !eventos.isEmpty()) {
            System.out.println("\nEventos de " + mes + ":");
            for (String evento : eventos) {
                System.out.println("- " + evento);
            }
        } else {
            System.out.println("Não há eventos para o mês de " + mes + ".");
        }
    }
}