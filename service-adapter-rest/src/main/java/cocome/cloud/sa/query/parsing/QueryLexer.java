package cocome.cloud.sa.query.parsing;

import java.util.regex.Pattern;

import de.kit.ipd.java.utils.framework.statemachine.CharStreamStateMachine;
import de.kit.ipd.java.utils.framework.statemachine.ILexer;
import de.kit.ipd.java.utils.framework.statemachine.ILexerVisitor;
import de.kit.ipd.java.utils.framework.statemachine.IState;
import de.kit.ipd.java.utils.framework.statemachine.IStateMachine;


public class QueryLexer implements ILexer<CharSequence> {

	/*************************************************************************
	 * STATIC
	 *************************************************************************/
	
	public static final IState<CharSequence> State0 = new State0();
	
	public static final IState<CharSequence> State1 = new State1();
	
	public static final IState<CharSequence> State2 = new State2();
	
	public static final IState<CharSequence> State3 = new State3();
	
	public static final IState<CharSequence> State4 = new State4();
	
	public static final IState<CharSequence> State5 = new State5();
	
	public static final IState<CharSequence> State6 = new State6();
	
	
	/*************************************************************************
	 * FIELDS
	 *************************************************************************/
	 
	 /**State-Machine which does the scanning of the source*/
	 private final IStateMachine<CharSequence> machine = new CharStreamStateMachine();
	 
	/*************************************************************************
	 * CONSTRUCTOR
	 *************************************************************************/
	 
	public QueryLexer() {
		
		machine.add(State0);
		machine.add(State1);
		machine.add(State2);
		machine.add(State3);
		machine.add(State4);
		machine.add(State5);
		machine.add(State6);
	}
	
	/*************************************************************************
	 * PUBLIC
	 *************************************************************************/
	 
	@Override
	public IStateMachine<CharSequence> getMachine() {
		return machine;
	}
	
	@Override
	public void addVisitor(ILexerVisitor<CharSequence> visitor) {
		machine.addVisitor(visitor);
	}
	
	/*************************************************************************
	 * STATES LOCAL CLASSES
	 *************************************************************************/


	/**************************************************************************
	 * STATE 0
	 *************************************************************************/
 
	public static class State0 implements IState<CharSequence> {
	    /**Index of State*/
	    public static final int INDEX = 0;
	    
	    /**transition to State0*/
	    private String transitionState0="[\\w\\W\\p{Punct}&&[^;\"]&&[^=\\r\\n]]";
	    /**transition to State6*/
	    private String transitionState6="[=]";
	    /**transition to Default*/
	    private String transitionDefault="[\\r]";
	   
	    @Override
	    public void run(IStateMachine<CharSequence> machine) {
	    	
	    	String next = String.valueOf(machine.getNext());
	        
	        if (Pattern.matches(transitionState0,next)) {
	            machine.appendToken(next);
	            machine.setNextState(0);
	        	return;
	        }
	        
	        else if (Pattern.matches(transitionState6,next)) {
	            machine.setNextState(6);
	            return;
	        }
	        
	        else if (Pattern.matches(transitionDefault,next)) {
	            machine.setNextState(0);
	            return;
	        }
	        
	        throw new IllegalStateException("Error:"
	        
	    	+transitionState0+", or,"
	    	+transitionState6+", or,"
	    	+transitionDefault+", or,"
	    	+ " was expected." + next
	        );
	    	
	    }
	    
	    @Override
		public int getIndex() {
			return INDEX;
		}
	}
	
	/**************************************************************************
	 * STATE 1
	 *************************************************************************/
 
	public static class State1 implements IState<CharSequence> {
	    /**Index of State*/
	    public static final int INDEX = 1;
	    
	    /**transition to State2*/
	    private String transitionState2="[\\w\\W\\p{Punct}&&[^;\"]&&[^\\r\\n]]";
	    /**transition to State3*/
	    private String transitionState3="[\"]";
	    /**transition to State4*/
	    private String transitionState4="[;\\n]";
	    /**transition to State1*/
	    private String transitionState1="[\\r]";
	   
	    @Override
	    public void run(IStateMachine<CharSequence> machine) {
	    	
	    	String next = String.valueOf(machine.getNext());
	        
	        if (Pattern.matches(transitionState2,next)) {
	            machine.appendToken(next);
	            machine.setNextState(2);
	        	return;
	        }
	        
	        else if (Pattern.matches(transitionState3,next)) {
	            machine.appendToken(next);
	            machine.setNextState(3);
	            return;
	        }
	        
	        else if (Pattern.matches(transitionState4,next)) {
	            machine.setNextState(4);
	            return;
	        }
	        
	        else if (Pattern.matches(transitionState1,next)) {
	            machine.setNextState(1);
	            return;
	        }
	        
	        throw new IllegalStateException("Error:"
	        
	    	+transitionState2+", or,"
	    	+transitionState3+", or,"
	    	+transitionState4+", or,"
	    	+transitionState1+", or,"
	    	+ " was expected." + next
	        );
	    	
	    }
	    
	    @Override
		public int getIndex() {
			return INDEX;
		}
	}
	
	/**************************************************************************
	 * STATE 2
	 *************************************************************************/
 
	public static class State2 implements IState<CharSequence> {
	    /**Index of State*/
	    public static final int INDEX = 2;
	    
	    /**transition to State2*/
	    private String transitionState2="[\\w\\W\\p{Punct}&&[^;\"]&&[^\\r\\n]]";
	    /**transition to State3*/
	    private String transitionState3="[\"]";
	    /**transition to State4*/
	    private String transitionState4="[;\\n]";
	    /**transition to Default*/
	    private String transitionDefault="[\\r]";
	   
	    @Override
	    public void run(IStateMachine<CharSequence> machine) {
	    	
	    	String next = String.valueOf(machine.getNext());
	        
	        if (Pattern.matches(transitionState2,next)) {
	            machine.appendToken(next);
	            machine.setNextState(2);
	        	return;
	        }
	        
	        else if (Pattern.matches(transitionState3,next)) {
	            machine.appendToken(next);
	            machine.setNextState(3);
	            return;
	        }
	        
	        else if (Pattern.matches(transitionState4,next)) {
	            machine.setNextState(4);
	            return;
	        }
	        
	        else if (Pattern.matches(transitionDefault,next)) {
	            machine.setNextState(2);
	            return;
	        }
	        
	        throw new IllegalStateException("Error:"
	        
	    	+transitionState2+", or,"
	    	+transitionState3+", or,"
	    	+transitionState4+", or,"
	    	+transitionDefault+", or,"
	    	+ " was expected." + next
	        );
	    	
	    }
	    
	    @Override
		public int getIndex() {
			return INDEX;
		}
	}
	
	/**************************************************************************
	 * STATE 3
	 *************************************************************************/
 
	public static class State3 implements IState<CharSequence> {
	    /**Index of State*/
	    public static final int INDEX = 3;
	    
	    /**transition to State3*/
	    private String transitionState3="[\\w\\W\\p{Punct}&&[^\"]]";
	    /**transition to State2*/
	    private String transitionState2="[\"]";
	   
	    @Override
	    public void run(IStateMachine<CharSequence> machine) {
	    	
	    	String next = String.valueOf(machine.getNext());
	        
	        if (Pattern.matches(transitionState3,next)) {
	            machine.appendToken(next);
	            machine.setNextState(3);
	        	return;
	        }
	        
	        else if (Pattern.matches(transitionState2,next)) {
	            machine.appendToken(next);
	            machine.setNextState(2);
	            return;
	        }
	        
	        throw new IllegalStateException("Error:"
	        
	    	+transitionState3+", or,"
	    	+transitionState2+", or,"
	    	+ " was expected." + next
	        );
	    	
	    }
	    
	    @Override
		public int getIndex() {
			return INDEX;
		}
	}
	
	/**************************************************************************
	 * STATE 4
	 *************************************************************************/
 
	public static class State4 implements IState<CharSequence> {
	    /**Index of State*/
	    public static final int INDEX = 4;
	    
	   
	    @Override
	    public void run(IStateMachine<CharSequence> machine) {
	    	
	        machine.callVisitor(getIndex(),machine.getToken());
	        machine.resetToken();
	        machine.setNextState(1);
	    	
	    }
	    
	    @Override
		public int getIndex() {
			return INDEX;
		}
	}
	
	/**************************************************************************
	 * STATE 5
	 *************************************************************************/
 
	public static class State5 implements IState<CharSequence> {
	    /**Index of State*/
	    public static final int INDEX = 5;
	    
	   
	    @Override
	    public void run(IStateMachine<CharSequence> machine) {
	        machine.callVisitor(getIndex(),machine.getToken());
	    }
	    
	    @Override
		public int getIndex() {
			return INDEX;
		}
	}
	
	/**************************************************************************
	 * STATE 6
	 *************************************************************************/
 
	public static class State6 implements IState<CharSequence> {
	    /**Index of State*/
	    public static final int INDEX = 6;
	    
	   
	    @Override
	    public void run(IStateMachine<CharSequence> machine) {
	    	
	        machine.callVisitor(getIndex(),machine.getToken());
	        machine.resetToken();
	        machine.setNextState(1);
	    	
	    }
	    
	    @Override
		public int getIndex() {
			return INDEX;
		}
	}
	
}
	
