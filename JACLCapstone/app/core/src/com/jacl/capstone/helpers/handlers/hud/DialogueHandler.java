package com.jacl.capstone.helpers.handlers.hud;

import com.jacl.capstone.hud.HUD;
import com.jacl.capstone.hud.world.DialogueBox;

public class DialogueHandler
{
	public HUD hud;
	
	public boolean showing_dialogue;
	public DialogueBox[][] boxes;
	public int current_box;
	public int current_dialogue;
	
	/**
	 * This is a special character that will be inserted into dialogue scripts.
	 * Upon getting the script of the dialogue, we will split it into multiple
	 * dialogue boxes using this character.
	 */
	private final String SPLIT_CHAR = "<s>";
	private final String END_DIAL = "<t>";
	
	public DialogueHandler(HUD hud)
	{
		this.hud = hud;
	}
	
	/**
	 * Create a textbox from the given text.
	 * @param file_text
	 */
	public void startDialogue(String file_text)
	{
		//Initialize world for dialogue box.
		showing_dialogue = true;
		current_box = 0;
		
		//Split the text into dialogues. These splits are denoted by the END_DIAL character.
		if(file_text.length() > 0){
			//Remove returns from the file_text.
			//file_text = file_text.replaceAll("", "");
			
			//Create an array of strings that represents the texts of the dialogues.
			String[] split_conversations = file_text.split(END_DIAL);
			
			//Each of these dialogues must be split further. These will represent multiple screens of dialogue per conversation.
			boxes = new DialogueBox[split_conversations.length][];
			for(int i = 0; i < split_conversations.length; i++){				
				//Create an array of strings that represents the texts of the dialogue boxes.
				String[] split_conversation_blocks = split_conversations[i].split(SPLIT_CHAR);
				
				//Each of these strings needs to be made into a dialogue box.
				boxes[i] = new DialogueBox[split_conversation_blocks.length];
				for(int j = 0; j < split_conversation_blocks.length; j++)
				{
					while(split_conversation_blocks[j].length() > 0 && !Character.isAlphabetic(split_conversation_blocks[j].charAt(0))){
						System.out.println(split_conversation_blocks[j].charAt(0));
						split_conversation_blocks[j] = split_conversation_blocks[j].substring(1);
					}
					
					boxes[i][j] = new DialogueBox(hud, split_conversation_blocks[j]);
				}
			}
		}
	}
	
	/**
	 * Move to the previous line(s) in the dialogue box.
	 */
	public void reverseDialogue()
	{
		current_box--;
		if(current_box < 0)
		{//Beginning of dialogue.
			current_box = 0;
		}
	}
	
	/**
	 * Move to the next line(s) in the dialogue box.
	 */
	public void forwardDialogue()
	{
		current_box++;
		if(current_box >= boxes[current_dialogue].length)
		{//End of this conversation.
			showing_dialogue = false;
			current_dialogue++;
		}
	}
	
	/**
	 * Handle the updating of dialogue. Should only be used for animations.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		
	}
	
	/**
	 * Draw the dialogue box. To do this, draw in the following order:<br>
	 * Border<br>
	 * Background<br>
	 * Text
	 */
	public void draw()
	{
		if(showing_dialogue && current_box >= 0 && current_box < boxes.length)
		{
			boxes[current_dialogue][current_box].draw();
		}
	}
}
