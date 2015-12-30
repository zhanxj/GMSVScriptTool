tree grammar GMSV_TREE;

 prog : stat -> ^(PROG stat); 
 stat : expr EOF  -> ^(STAT expr)