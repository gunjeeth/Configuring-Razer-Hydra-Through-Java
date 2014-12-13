package project.razerhydra;

import java.util.Arrays;

import com.sixense.ControllerData;
import com.sixense.Sixense;
import com.sixense.utils.ButtonStates;
import com.sixense.utils.ControllerManager;
import com.sixense.utils.ManagerCallback;
import com.sixense.utils.enums.EnumControllerDesc;
import com.sixense.utils.enums.EnumGameType;
import com.sixense.utils.enums.EnumSetupStep;

public class TestRazerHydra implements ManagerCallback{
		
	private ControllerData [] controllerData = {new ControllerData(), new ControllerData(), new ControllerData(), new ControllerData()};
	private boolean menVisible = false;
	private ButtonStates state1;
	private ButtonStates state2;
	
	
	public static void main(String[] args) {
		TestRazerHydra start = new TestRazerHydra();
		start.run();
	}

	
	public TestRazerHydra() {
		System.out.println("Initializing ...");
		Sixense.init();
		ControllerManager.getInstance().setGameType(EnumGameType.ONE_PLAYER_TWO_CONTROLLER);
		ControllerManager.getInstance().registerSetupCallback(this);
		
		state1 = new ButtonStates();
		state2 = new ButtonStates();
	}
	
	
	public void run()
	{
		System.out.println("Running ...");
		while(true)
		{
			Sixense.setActiveBase(0);
			Sixense.getAllNewestData(controllerData);
			ControllerManager.getInstance().update(controllerData);
			
			if(!menVisible)
			{
				int leftIndex = ControllerManager.getInstance().getIndex(EnumControllerDesc.P1L);
				int rightIndex = ControllerManager.getInstance().getIndex(EnumControllerDesc.P1R);
				
				state1.update(controllerData[leftIndex]);
				state2.update(controllerData[rightIndex]);
				
				System.out.println("Pos1: " + Arrays.toString(controllerData[leftIndex].pos));
				System.out.println("| Pos2: " + Arrays.toString(controllerData[rightIndex].pos));
							
				//System.out.println("Button: ");
			} 
		}
	}
	

	@Override
	public void onCallback(EnumSetupStep arg0) {
		if(ControllerManager.getInstance().isMenuVisible())
		{
			menVisible = true;
			System.out.println(ControllerManager.getInstance().getStepString());
		}
		else
		{
			menVisible = false;
		}
	}
}