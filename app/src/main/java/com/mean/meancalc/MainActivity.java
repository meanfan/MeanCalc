package com.mean.meancalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_input;
    private TextView tv_rst;
    private Button btn_num[]; //0,1,2,3,4,5,6,7,8,9,dot
    private Button btn_operator[]; //+,-,×,÷,√▔ ,= ,CE ,C
    private int inputStage;
    private double leftVal,rightVal,rstVal;
    private int operator;
    class Input{
        public static final int TYPE_OPERATOR = 0, TYPE_NUM = 1;
        private int type;
        private double val;
        Input(int t,int v){
            type = t;
            val = v;
        }
        int getType(){
            return type;
        }
        double getValue(){
            return val;
        }
    }
    private Stack<Input> inputStack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputStack = new Stack<Input>();
        tv_input = (TextView)findViewById(R.id.tv_input);
        tv_rst = (TextView)findViewById(R.id.tv_rst);
        btn_num = new Button[11];
        btn_num[0] = (Button)findViewById(R.id.btn_0);
        btn_num[1] = (Button)findViewById(R.id.btn_1);
        btn_num[2] = (Button)findViewById(R.id.btn_2);
        btn_num[3] = (Button)findViewById(R.id.btn_3);
        btn_num[4] = (Button)findViewById(R.id.btn_4);
        btn_num[5] = (Button)findViewById(R.id.btn_5);
        btn_num[6] = (Button)findViewById(R.id.btn_6);
        btn_num[7] = (Button)findViewById(R.id.btn_7);
        btn_num[8] = (Button)findViewById(R.id.btn_8);
        btn_num[9] = (Button)findViewById(R.id.btn_9);
        btn_num[10] = (Button)findViewById(R.id.btn_dot);
        btn_operator = new Button[8];
        btn_operator[0] = (Button)findViewById(R.id.btn_plus);
        btn_operator[1] = (Button)findViewById(R.id.btn_minus);
        btn_operator[2] = (Button)findViewById(R.id.btn_multiply);
        btn_operator[3] = (Button)findViewById(R.id.btn_divided);
        btn_operator[4] = (Button)findViewById(R.id.btn_sqrt);
        btn_operator[5] = (Button)findViewById(R.id.btn_equal);
        btn_operator[6] = (Button)findViewById(R.id.btn_ce);
        btn_operator[7] = (Button)findViewById(R.id.btn_c);
        for(int i=0;i<11;i++)
            btn_num[i].setOnClickListener(this);
        for (int i=0;i<8;i++)
            btn_operator[i].setOnClickListener(this);
        initInput();
    }

    @Override
    public void onClick(View v) {
        int resid = v.getId();
        String input = new String();
        if(resid == R.id.btn_equal) {
            if(operator != 0) {
                calResult();
                tv_input.setText(tv_input.getText().toString() + tv_rst.getText().toString() + "=");
                tv_rst.setText("" + rstVal);
                initInput();
            }
        }else if(resid == R.id.btn_ce) {

            if(tv_rst.getText().toString().matches("0")) {
                tv_input.getText().subSequence(0, tv_input.getText().toString().length() - 2);
            }
            tv_rst.setText("0");
        }else if(resid == R.id.btn_c) {
            tv_input.setText("");
            tv_rst.setText("0");
            initInput();
        }else if(resid == R.id.btn_plus) {
            tv_input.setText(tv_input.getText().toString() + tv_rst.getText().toString() + "+");
            subOperate();
            operator = 1;
        }else if(resid == R.id.btn_minus){
            tv_input.setText(tv_input.getText().toString() + tv_rst.getText().toString() + "-");
            subOperate();
            operator = 2;
        }else if(resid == R.id.btn_multiply){
            if(operator == 1 || operator == 2)
                tv_input.setText("(" + tv_input.getText().toString() + tv_rst.getText().toString() + ")×");
            else
                tv_input.setText(tv_input.getText().toString() + tv_rst.getText().toString() + "×");
            subOperate();
            operator = 3;
        }else if(resid == R.id.btn_divided){
            if(operator == 1 || operator == 2)
                tv_input.setText("(" + tv_input.getText().toString() + tv_rst.getText().toString() + ")÷");
            else
                tv_input.setText(tv_input.getText().toString() + tv_rst.getText().toString() + "÷");
            subOperate();
            operator = 4;
        }else if(resid == R.id.btn_sqrt){
            inputStage = 2;
            operator = 5;
        }else {
            if (resid == R.id.btn_dot)
                input = ".";
            else
                input = ((Button) v).getText().toString();
            switch(inputStage){
                case 0:
                    tv_rst.setText(input);
                    leftVal = Double.valueOf(tv_rst.getText().toString());
                    inputStage = 1;
                    break;
                case 1:
                    tv_rst.setText(tv_rst.getText().toString() + input);
                    leftVal = Double.valueOf(tv_rst.getText().toString());
                    break;
                case 2:
                    tv_rst.setText(input);
                    rightVal = Double.valueOf(tv_rst.getText().toString());
                    inputStage = 3;
                    break;
                case 3:
                    tv_rst.setText(tv_rst.getText().toString() + input);
                    rightVal = Double.valueOf(tv_rst.getText().toString());
                    break;
            }
        }

    }

    private void calResult() {
        switch (operator) {
            case 1:
                rstVal = leftVal + rightVal;
                break;
            case 2:
                rstVal = leftVal - rightVal;
                break;
            case 3:
                rstVal = leftVal * rightVal;
                break;
            case 4:
                rstVal = leftVal / rightVal;
                break;

        }
    }
    public void subOperate(){
        if(operator != 0) {
            calResult();
            leftVal = rstVal;
            rightVal = 0;
        }
        inputStage = 2;
        tv_rst.setText("0");
    }
    public void initInput(){
        inputStack.clear();
        inputStage = 0;
        leftVal = rightVal = operator  = 0;
    }

}
