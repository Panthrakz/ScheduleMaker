# ScheduleMaker

OK, making it all work:


1) Format the excel files appropriately:

The STUDENTS excel needs to be formatted like so:

Student Name		Class1		Class2		...		Class5		Chores		Chores

You DO need to fill it in all the way to the 8th cell with 'Chores.'

The TEACHERS excel goes like:

Teacher Name		Class1		Class2		...		Class5

You DON'T need to input FreePeriod - the program knows how to fill those in.

The SECTIONS excel goes:

Class1		Teacher Name (1)		NumberofSections
Class1		Teacher Name (2)		NumberofSections

It will handle multiple teachers teaching the same class by creating sections just for them!


2) Make sure the program knows where to find and read the excel files:

Default string TEACHERPATH = "c:/Users/steve/OneDrive/Desktop/Scheduleproject/Teachers.xlsx"; go ahead and change the path to reflect
wherever you want the files to be. 

These ___PATH strings need to be updated in THREE different classes: MasterScheduleCreator, ScheduleEvaluator, and ScheduleFactory. 


3) Tweaking search parameters: go to the line in MasterScheduleCreator inside the populationUpdate method that says, 

	if(data.getBestCandidateFitness() > XXX){

If the fitness of a given schedule is above XXX, it will print the schedule out inside java for you. It won't stop printing out highly fit schedules 
after it starts until you tweak ...

4) Stop condition:  in main, the line with

   	 Schedule result = engine.evolve(p, q, new TargetFitness(r, true));

determines how the program thinks. Its going to generate p schedules initially and make sure that each generation has p members; it's going to pass 
q of the fittest members from one generation to the next without performing any evolution on it (elitism count); and it's going to keep working on
evolving the population until someone has a fitness of r.

5) Other toys

I've started to write, but have not finished, a method (Optimizer, in MasterScheduleCreator) that takes the result from (4) and rearranges periods 
to make sure that, for example, teachers with multiple sections of a class (1) get to teach them consecutively and (2) have them grouped in ABCD 
or EFG periods.
