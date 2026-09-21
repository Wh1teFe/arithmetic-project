import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String,String> param = new HashMap<>();
        for(int i=0;i<args.length;i++){
            if(args[i].startsWith("-")){
                if(i+1 < args.length && !args[i+1].startsWith("-")){
                    param.put(args[i], args[i+1]);
                    i++;
                }else{
                    param.put(args[i], "");
                }
            }
        }

        if(param.containsKey("-e") && param.containsKey("-a")){
            try {
                String ePath = param.get("-e");
                String aPath = param.get("-a");
                List<String> exercises = Util.readFile(ePath);
                List<String> answers = Util.readFile(aPath);
                List<Integer> correct = new ArrayList<>();
                List<Integer> wrong = new ArrayList<>();

                for(int i=0;i<exercises.size();i++){
                    if(i >= answers.size()) {
                        wrong.add(i+1); continue;
                    }
                    String exerciseLine = exercises.get(i);
                    String expr = exerciseLine.substring(0, exerciseLine.lastIndexOf("=")).trim();
                    Fraction stdAns = Evaluator.evaluate(expr);
                    Fraction userAns = Fraction.parse(answers.get(i));

                    if(stdAns.equals(userAns)){
                        correct.add(i+1);
                    }else {
                        wrong.add(i+1);
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Correct: ").append(correct.size()).append(" ");
                sb.append(correct.toString().replace("[","(").replace("]",")")).append("\n");
                sb.append("Wrong: ").append(wrong.size()).append(" ");
                sb.append(wrong.toString().replace("[","(").replace("]",")"));
                Util.writeFile("Grade.txt", Collections.singletonList(sb.toString()));
                System.out.println("批改完成，输出Grade.txt");
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }

        if(!param.containsKey("-r")){
            System.out.println("参数错误！必须提供 -r。用法：java Main -n 10 -r 10");
            return;
        }
        int n = Integer.parseInt(param.getOrDefault("-n","10"));
        int r = Integer.parseInt(param.get("-r"));
        if(r < 1){
            System.out.println("-r 必须 >=1");
            return;
        }

        Set<String> existSig = new HashSet<>();
        List<String> exList = new ArrayList<>();
        List<String> ansList = new ArrayList<>();

        while(exList.size() < n){
            Expression exp = Expression.createOne(r);
            String sig = exp.getSignature();
            if(existSig.contains(sig)) continue;
            existSig.add(sig);
            exList.add(exp.getExprStr() + " = ");
            ansList.add(exp.getValue().toString());
        }
        try {
            Util.writeFile("Exercises.txt", exList);
            Util.writeFile("Answers.txt", ansList);
            System.out.println("生成完成！共"+n+"道题目");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}




