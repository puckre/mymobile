#include <stdio.h>
#include <stdlib.h> 

typedef struct _node
{
	int key;
	struct _node *prev;
	struct _node *next;
} Queue;

Queue *head, *tail;

void init_queue(void) // 큐 초기화
{
	head = (node*)malloc(sizeof(node));
	tail = (node*)malloc(sizeof(node));

	head->prev = head;
	head->next = tail;
	tail->prev = head;
	tail->next = tail;
}

void put(int k)   // put
{
	node *t;

	t = (node*)malloc(sizeof(node));
	t->key = k;

	tail->prev->next = t;
	t->prev = tail->prev;
	tail->prev = t;
	t->next = tail;
}

void print_queue(void) // 출력
{
	node *now;
	
	for(now = head->next; now != tail ; now = now->next)
	{
		if( now->key == '+' || now->key == '-' || now->key == '*' || now->key == '/' )
		{        // 만약 큐에 들어있는 값이 문자라면
			printf("%c ", now->key); // 문자(%c)로 출력
		}
		else       // 숫자라면
			printf("%d ", now->key); // 숫자(%d)로 출력
	} 
}

void calc_prefix(void)
{
	int a,b,c,d;
	int t;

	node *now;

	now=head->next;

	for(t=0;t<9;t++)
	{
		if(((now->data == '+') || (now->data == '-') || (now->data == '*') || (now->data == '/')) && !( (now->next->key == '+') || (now->next->key == '-') || (now->next->key == '*') || (now->next->key == '/') )
			&& !( (now->next->next->key == '+') || (now->next->next->key == '-') || (now->next->next->key == '*') || (now->next->key == '/') ) )
		{ // <현재 노드, 다음 노드, 다다음 노드>의 값이 <연산자, 숫자, 숫자> 즉, prefix연산을 할 수 있는 형태로 되어있다면
			d = now->key;    // 연산자를 d에 넣고
			a = now->next->key;   //
			b = now->next->next->key; // 숫자를 a와 b에 넣어

			if(d == '+')
			{
				c=a+b;     // 연산자에 따른 연산을 해서 값을 c에 넣고
				now->key=c;    // 연산자가 들어있던 자리에 그 값을 넣는다
			}
			else if(d == '-')
			{
				c=a-b;
				now->key=c;
			}
			else if(d == '*')
			{
				c=a*b;
				now->key=c;
			}
			else if(d == '/')
			{
				c=a/b;
				now->key=c;
			}
			now->next = now->next->next->next;  // 현재 노드의 다음을
			now->next->next->next->prev = now;  // 숫자가 들어있던 노드의 다음으로 연결한다
			now=now->next;       // 계속 알아보기 위해 다음 노드로 이동
		}
		else
		{
			now=now->next;       // prefix연산을 할 수 없다면 바로 다음 노드로 이동
		}
	}
}

int main(void) 
{ 
	init_queue();

	put('-');put('+');put('*');put(9);put('+');put(2);put(8);put('*');put('+');put(4);put(8);put(6);put(3);

	printf("queue를 처음 출력하면 : ");
	print_queue(); 
	printf("\n");

	calc_prefix(); 
	printf("queue를 다시 출력하면 : ");
	print_queue();
	printf("\n");
	
	return 0;
}