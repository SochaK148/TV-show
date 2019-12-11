package com.sample;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
	
	private Integer width;
	private JFrame frame;
	private JTextPane textPane;
	private ArrayList<JButton> buttons;
	private String chosenAnswer;
	
	public GUI() {
		this.width = 530;
		
		this.frame = new JFrame("What TV show are you watching?");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(width, 240);
		this.frame.setLayout(new FlowLayout());
		
		this.textPane = new JTextPane();
		this.textPane.setPreferredSize(new Dimension(width-30, 70));
		this.textPane.setEditable(false);
		this.textPane.setOpaque(false);
		
		Font font = new Font("SansSerif", Font.BOLD, 16);
		this.textPane.setFont(font);
		
		this.frame.add(this.textPane, BorderLayout.NORTH);
		
		this.chosenAnswer = "";
		this.buttons = new ArrayList<>();
	}
	
	private void drawButtons(ArrayList<String> answers)
	{
		for(JButton button: this.buttons) {
			this.frame.remove(button);
		}
		
		if(answers!=null) {
			for(String answer: answers)
			{
				JButton button = new JButton(answer);
				button.addActionListener( new ActionListener() 
				{
	    			public void actionPerformed(ActionEvent e) 
	    			{
	    				chosenAnswer = e.getActionCommand();
	    			}
	    		} );
				this.buttons.add(button);
				this.frame.add(button);
			}
		}

		this.frame.revalidate();
		this.frame.repaint();
	}
	
	public String questionHandler(String question, ArrayList<String> answers) throws InterruptedException
	{
		this.textPane.setText(question);
		this.drawButtons(answers);
		this.frame.setVisible(true);
		
		while(this.chosenAnswer == "") {
			Thread.sleep(100);
		}
		
		String answer = this.chosenAnswer;
		this.chosenAnswer = "";
		    		
		return answer;
	}
	
}
