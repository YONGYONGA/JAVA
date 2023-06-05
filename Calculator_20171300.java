import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.Stack;
import javax.swing.JTextArea;
import javax.swing.JTextField;
class WindowDestroyer extends WindowAdapter{
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}
public class Calculator_20171300 implements ActionListener{
	private Frame f;
	private TextArea tf;
	public String aa="",nn="";
	private int left_braket=0;
	private int in_number=0;
	private int isend=0;
	private int iserror=0;
	private ArrayList<String> equation=new ArrayList<String>();
	private ArrayList<String> husun=new ArrayList<String>();
	private Stack<String> stack=new Stack<>();
	public int priority(String operator){
		
        if(operator.equals("(") || operator.equals(")")){
            return 0;
        } else if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if (operator.equals("x") ||operator.equals("��")) {
            return 2;
        }else if (operator.equals("!")|| operator.equals("%")) {//�켱���� �ǹ̾���
            return 100;
        }else if (operator.equals("��") || operator.equals("log")|| operator.equals("ln")|| operator.equals("^")) {
            return -2;//���������� �켱���� �ǹ̾���. �׳�)���ö����� stack���� ��Ƽ��
        }
        return -1;
    }
	public Calculator_20171300(){

		f=new Frame("Calculator");
		//f.setLayout(NULL);
		f.setSize(660,400);
		f.setLayout(null);
		f.setResizable(false);
		//f.setLayout();
		//Panel num=new Panel();
		//num.setSize(700,200);
		//num.setBackground(Color.white);
		Panel butt=new Panel();
		//butt.setSize(200,200);
		butt.setLayout(new GridLayout(5,5,3,3));
		butt.setBounds(30,160,600,220);
		tf=new TextArea(3,10);
		//tf=new TextArea()
		//tf.setSize(200,200);
		//tf.setHori
		tf.setFont(new Font("Arail",Font.BOLD,27));
		tf.setBounds(30,30,600,120);
		tf.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		tf.addKeyListener(new NameHandler());
		//Graphics g=tf.getGraphics();;
		//g.setFont(new Font("����ü",Font.BOLD,5));
		//g.drawString("dd",30,30);
		//num.add(tf,BorderLayout.CENTER);
		Button []calcu=new Button[25];
		calcu[0]=new Button("x!");
		calcu[1]=new Button("(");
		calcu[2]=new Button(")");
		calcu[3]=new Button("%");
		calcu[4]=new Button("AC");
		calcu[5]=new Button("ln");
		calcu[6]=new Button("7");
		calcu[7]=new Button("8");
		calcu[8]=new Button("9");
		calcu[9]=new Button("��");
		calcu[10]=new Button("log");
		calcu[11]=new Button("4");
		calcu[12]=new Button("5");
		calcu[13]=new Button("6");
		calcu[14]=new Button("x");
		calcu[15]=new Button("��");
		calcu[16]=new Button("1");
		calcu[17]=new Button("2");
		calcu[18]=new Button("3");
		calcu[19]=new Button("-");
		calcu[20]=new Button("x^y");
		calcu[21]=new Button("0");
		calcu[22]=new Button(".");
		calcu[23]=new Button("=");
		calcu[24]=new Button("+");
		for(int i=0;i<25;i++) {
			//calcu[i].setBounds(1,1,3,54);
			calcu[i].setFont(new Font("gogo",Font.PLAIN,40));
			calcu[i].addActionListener(this);
			butt.add(calcu[i]);
		}
		f.add("North",tf);
		f.add(butt);
		
		WindowDestroyer listner=new WindowDestroyer();
		f.addWindowListener(listner);
		f.setVisible(true);

		
	}
	class NameHandler extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			char c=e.getKeyChar();
			int i=e.getKeyCode();
			System.out.println(i);
			if(Character.isDigit(c)||c=='^'||c=='!'||c=='+'||c=='-'||c=='x'||c=='/'||c=='%'||c=='('||c==')'||c=='.') {
					//����ó���� Ŭ���������� ����

			}
			else if(c=='\n'){
				//aa=tf.getText();
				//System.out.println(aa);
				//System.out.println("hey~");
				calculate();
			}
			else if(i==8) {
				int leng=tf.getText().length();
				if(leng>1) {
					String sub=tf.getText().substring(0,leng-1);
					String two=tf.getText().substring(leng-2,leng-1);
					tf.setText(sub);
					tf.append(two);
				}
				else if(leng==1) {
					tf.setText("");
				}
			}
			else {
				e.consume();			
				}


		}
	}
	public void keyTyped(KeyEvent e) {	}
	public void keyReleased(KeyEvent e) {}
	public void actionPerformed(ActionEvent e) {
		if(iserror==1) {
			tf.setText("");
			iserror=0;
			isend=0;
		}
		String data=e.getActionCommand();
		if(data.equals("=")) {
			while(left_braket>0) {
				tf.append(")");
				left_braket--;
			}
			if(!tf.getText().equals(""))
			calculate();

			//tf.append(data);
		}
		else if(data.equals("x!")) {
			tf.append("!");
			isend=0;
		}
		else if(data.equals("x^y")) {
			tf.append("^");
			tf.append("(");
			left_braket++;
			isend=0;
		}
		else if(data.equals("��")||data.equals("ln")||data.equals("log")) {
			if(isend==1) {
				tf.setText("");
				isend=0;
			}
			tf.append(data+"(");
			left_braket++;
		}
		else if(data.equals("(")) {
			if(isend==1) {
				tf.setText("");
				isend=0;
			}
			tf.append(data);
			in_number=0;
			left_braket++;
		}
		else if(data.equals(")")) {
			if(left_braket>0 && in_number!=0) {
				left_braket--;
				tf.append(data);
			}
		}
		else if(data.equals("AC")) {
			left_braket=0;
			in_number=0;
			tf.setText("");
		}
		else {
			if(tf.getText().length()!=0&&tf.getText().charAt(tf.getText().length()-1)==')'&& isNumeric(data)) {
				tf.append("x");
			}
			tf.append(data);
			if(left_braket>0 && isNumeric(data)) {
				in_number++;
			}
			isend=0;
		}
		
		
	}
    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
    //�ڿ� parse�Ҷ� ��ȣ�� xó�� �Ǿ�� �ϴϱ�, "("�� �ö�!, �ڿ� �ִ� ���� ���ڰų� )�̸� �����ڿ� x�߰����ֱ�
	private void Parsing(String input) {
		equation.clear();
		nn="";
		int i,array_index=0;
		for(i=0;i<input.length();i++) {
			char c=input.charAt(i);
			if(c=='-'||c=='x'||c=='-'||c=='+'||c=='��'||c=='^'||c==')'||c=='!'||c=='%'){
				if(c=='-' && nn.equals("")) {nn="-";continue;}
				if(!nn.equals("")) {		
				equation.add(nn);
				nn="";
				array_index++;
				}
				equation.add(c+"");
				array_index++;
			}
			else if(c=='��') {
				
				if(!nn.equals("")) {
					equation.add(nn);
					equation.add("x");
					equation.add("��");
					array_index++;
					array_index++;
					array_index++;
					nn="";
				}
				else if(array_index>0) {
					String past=equation.get(array_index-1);
					if(past.equals(")")||past.equals("!")||past.equals("%")) {
						equation.add("x");
						equation.add("��");
						//array_index++;
						array_index++;
						array_index++;
						nn="";
					}
					else {
						equation.add("��");
						array_index++;
					}
				}
				else {
					equation.add("��");
					array_index++;
				}
			}
			else if(c=='(') {
				if(!nn.equals("")) {
					equation.add(nn);
					equation.add("x");
					equation.add("(");
					array_index++;
					array_index++;
					array_index++;
					nn="";
				}
				else if(array_index>0) {
					String past=equation.get(array_index-1);
					if(past.equals(")")||past.equals("!")||past.equals("%")) {
						equation.add("x");
						equation.add("(");
						//array_index++;
						array_index++;
						array_index++;
						nn="";
					}
					else {
						equation.add("(");
						array_index++;
					}
				}
				else {
					equation.add("(");
					array_index++;
				}
			}
			else if(c=='l') {
				if(!nn.equals("")) {
					equation.add(nn);
					equation.add("x");
					array_index++;
					array_index++;
					nn="";
				}
				else if(array_index>0) {
					String past=equation.get(array_index-1);
					if(past.equals(")")||past.equals("!")||past.equals("%")) {
						equation.add("x");
						array_index++;
						}
					}
				if(tf.getText().substring(i,i+3).equals("lnv")) {
					equation.add("lnv");
					array_index++;
					i=i+2;
				}
				else if(tf.getText().substring(i,i+2).equals("ln")) {
					equation.add("ln");
					array_index++;
					i=i+1;
				}
				else if(tf.getText().substring(i,i+3).equals("log")) {
					equation.add("log");
					array_index++;
					i=i+2;
				}		
			}
			else {
				if(array_index>0) {
					String past=equation.get(array_index-1);
					if(past.equals(")")||past.equals("!")||past.equals("%")) {
						equation.add("x");
						array_index++;
						}
					}
				nn=nn+c;
			}
		}
		equation.add(nn);
	}
	private String[] real_equation() {
		stack.clear();
		husun.clear();
		for (int i = 0; i < equation.size(); ++i) {
           String now=equation.get(i);
           if(now.equals(""))continue;
           switch(now) {
           case "+":
           case "-":
           case "x":
           case "��":
           //case "%":   //�̰͵� !�� ����. �׳� 100��������
           //case "!": //�� �׳� �����ڸ��� husun����(�켱���� �ſ����)
           //case "^": //��ģ���� ������ ()�� �����ϴ�, �켱���� �Ű澲������ ')'���� ó�� �α׵� ��������
        	   //���þ��� �켱������ ������ ���þȿ� �ִ��� �Ѱܳ���    �ȿ��ִ���>=���þ�?
        	   while (!stack.isEmpty() && priority(stack.peek()) >= priority(now)) {
                   husun.add(stack.pop()); //�Ѱܳ���
               }
               stack.push(now);
               break;
           case "(":
               stack.push(now);
               break;
           case ")":
               while(!stack.isEmpty() && !stack.peek().equals("(")){                    	                              	
               		husun.add(stack.pop());                  	
               }
               stack.pop();
               while(!stack.isEmpty() && ((stack.peek().equals("��"))||(stack.peek().equals("^"))||(stack.peek().equals("ln"))||(stack.peek().equals("log")))) {
            	   husun.add(stack.pop()); 
               }
            
               break;
           case "!": //�켱���� ���ɾ��� �׳� �����(husun�� �׳� ��)
           case "%":
        	   husun.add(now);
        	   break;
           case "^":
           case "log":
           case "ln":   //�ȿ��� ��Ƽ�Ⱑ ���� )������ �˻��ϸ� husun�� ����
           case "��":
        	   stack.push(now);
        	   break;
        	   
           default:
               husun.add(now);
           }

        }
        while (!stack.isEmpty()) {
            husun.add(stack.pop());
        }
		 String[] result = new String[husun.size()];

	        for(int i = 0; i < husun.size(); i++) {      
	        	result[i]=husun.get(i); 
	        }
	        
	        return result;
	}
    public void calculate() {
    	if(left_braket!=0) {
    		tf.append("\nError. Invalid braket number.");
        	clear_variable();
    		iserror=1;
    		return;
    	}
    	String first=tf.getText();
    	Parsing(first);
    	int isint=1;
       /* for (int i = 0; i < equation.size(); ++i) {
            System.out.print(equation.get(i) + "   ");
        }
        System.out.println("");*/  //�ϴ� ����� ����, ��ȣ �ν��ϴ��� equation ���ο�
        String[] husuns=real_equation();
        /*for(int i=0;i<husuns.length;i++) {
            System.out.print(husuns[i] + "  ");
        }
        Sy��tem.out.println("");*/   //����� �ļ��� �Է½��ÿ� ������ Ȯ�ο�
        Stack<Double> lets=new Stack<>();
        try {
        NAMED:
        for(String now : husuns) {
        	Double a;
        	if(!now.equals("+")&&!now.equals("-")&&!now.equals("x")&&!now.equals("%")&&!now.equals("��")&&!now.equals("log")&&!now.equals("ln")&&
        			!now.equals("^")&&!now.equals("!")&&!now.equals("��")) {
        		/*if(isint==1) { //�������� �Ǽ����� Ȯ��. ������ �������ٸ�, �׳� ������ ����?�α״� ��¼��?
        			for(int ii=0;ii<now.length();ii++) {
        				char ca=now.charAt(ii);
        				if(ca=='.') {
        					isint=0;
        					break;
        				}
        			}
        		}*/
        		try {
        			a=Double.parseDouble(now);
        			lets.push(a);
        		}
        		catch(java.lang.NumberFormatException e){
        			iserror=1;
        			tf.append("\nError. multiple points(Invalid number)");
        			break NAMED;
        		}
        	}
        	else if(now.equals("log")||now.equals("ln")||now.equals("!")||now.equals("��")||now.equals("%")) {
        		double one=lets.pop();
        		double re=22;
        		switch(now) {
        		case "log":
        			re=Math.log10(one);
        			lets.push(re);
        			break;
        		case "ln":
        			re=Math.log(one);
        			lets.push(re);
        			break;
        		case "!":
        			int two=(int)one;
        			if((double)two!=one) {
        				iserror=1;
            			tf.append("\nError. Can't calculate float factorial");
            			break NAMED;
        			}
        			double fact=fact(two);
        			lets.push(fact);
        			break;
        		case "��":
        			re=Math.sqrt(one);
        			lets.push(re);
        			break;   
        		case "%":
        			lets.push(one/100);
        			break;
        		}
        		if(Double.isNaN(re)) {
    				iserror=1;
        			tf.append("\nError. There is minus in log, ln, ��");
        			break NAMED;
        		}
        	}
        	else {
        		double one=lets.pop();
        		double two=lets.pop();
        		double re=22;
        		switch(now) {
        		case "+":
        			re=one+two;
        			lets.push(re);
        			break;
        		case "-":
        			re=two-one;
        			lets.push(re);
        			break;
        		case "^":
        			re=Math.pow(two,one);
        			lets.push(re);
        			break;
        		case "x":
        			re=two*one;
        			lets.push(re);
        			break;
        		case "��":
        			try {
        				double ree=two/one;
        				lets.push(ree);
        			}
        			catch(java.lang.ArithmeticException e) {
        				iserror=1;
            			tf.append("\nError. Can't divide by zero. But this is infinity");
            			break NAMED;
        			}
        			break;
        		}
        		if(Double.isNaN(re)) {
    				iserror=1;
        			tf.append("\nError.Calculate impossible,(ex. 0*infinity)");
        			break NAMED;
    			}
        	}
        }
        }
        catch(Throwable e) {
			iserror=1;
			tf.append("\nError. Invalid sequence");
        }
        if(iserror==1) {
        	clear_variable();
        	return;
        }
        //�ǹ̾��� �� �Ҽ��� �ڸ��� �����ؼ� �����ֱ�
        double real_re=lets.pop();
        String test_e=""+real_re;
        int test_E=0;
        //�ڸ��� ��ûŬ�� E�ڵ������̴�, �װ� Ȯ�ο�
        for(int abc=0;abc<test_e.length();abc++) {
        	if(test_e.charAt(abc)=='E') {
    //    		tf.setText(test_e);
        		test_E=1;
        		break;
        	}
        }
        if(test_E==1) {
        	tf.setText(test_e);
        }
        else {
	        String kkk=String.format("%.11f", real_re);
			for(int i=kkk.length()-1;i>0;i--) {
	
				if(kkk.charAt(i)=='0' ||kkk.charAt(i)=='.') {
	
					if(kkk.charAt(i)=='.') {
						kkk=kkk.substring(0,i);
						break;
					}
					else
						kkk=kkk.substring(0,i);
				}
				else {
					break;
				}
			}
			tf.setText(kkk);
        }
    	//tf.setText(kkk);
    	//tf.setText(""+real_re);
    	clear_variable();
    
    	//System.out.println((isNumeric(tf.getText())));
    	//System.out.println(!tf.getText().equals(""));
		//System.out.println("��걸�� ����");
	}
	public double fact(int aaa) {
		int minus=1;
		if(aaa<0) {
			minus=-1;
		}
		int re=1;
		for(int i=1;i<=minus*aaa;i++) {
			re=re*i;
		}
		return (double)re*minus;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculator_20171300 start=new Calculator_20171300();
	}
	public void clear_variable() {//�׳� Ȥ�ø��� �����Լ�. ����������
		left_braket=0;
		in_number=0;
		isend=1;  //����쿡 ���곡���� Ŭ���ϸ� �׳� ȭ���ʱ�ȭ�� �ȴ�. �װ� �ľ�(log,ln,(,��Ʈ)
		//is_error�� ����üũ�Ҷ� �ʿ���. �׶��׶� �ʱ�ȭ���ֱ�
		
	}

}
